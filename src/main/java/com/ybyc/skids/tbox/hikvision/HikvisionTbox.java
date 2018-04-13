package com.ybyc.skids.tbox.hikvision;

import com.ybyc.gateway.nettyplus.core.codec.DirectiveCodec;
import com.ybyc.gateway.nettyplus.core.codec.LengthFieldBasedFrameEncoder;
import com.ybyc.gateway.nettyplus.core.codec.XorChecker;
import com.ybyc.gateway.nettyplus.core.handler.GenericObjectChannelInboundHandler;
import com.ybyc.skids.common.location.Location;
import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.tbox.hikvision.frame.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class HikvisionTbox extends BaseTbox {

    public static final byte STATUS_RENTABLE = 0x00;
    public static final byte STATUS_SUBSCRIBED = 0x01;
    public static final byte STATUS_RENT = 0x02;
    public static final byte STATUS_MAINTAIN = 0x03;
    public static final byte STATUS_FAULT = 0x04;
    public static final byte STATUS_LOWPOWER = 0x05;

    public static final byte STATUS_CHARGING = 0x03;
    public static final byte STATUS_UNCHARGING = 0x00;
    public static final byte STATUS_ACC_ON = 0x00;
    public static final byte STATUS_ACC_OFF = 0x01;

    private byte status;

    HikvisionContext context;
    Channel channel;

    public HikvisionTbox(Car car, HikvisionContext context) {
        super(car);
        this.context = context;
    }

    @Override
    public boolean on() {
        boot();
        return true;
    }

    @Override
    public boolean off() {
        channel.close();
        return true;
    }

    @Override
    public void pushStatus() {
        super.pushStatus();
        if(Objects.isNull(channel)){
            return;
        }

        StatusData statusData = new StatusData();
        statusData.setPreStatus(STATUS_RENTABLE);
        statusData.setStatus(STATUS_RENTABLE);

        Location.Point point = new Location(car.getStatus().getLongitude(),car.getStatus().getLatitude()).toWgs84();

        statusData.setLongitude((int)(point.lng*Math.pow(10,6)));
        statusData.setLatitude((int)(point.lat*Math.pow(10,6)));

        statusData.setSoc((byte)car.getStatus().getSoc());
        statusData.setCard((byte)-1);
        statusData.setOdo((int)car.getStatus().getOdo());
        statusData.setCurrent((short)350);
        statusData.setCharging(car.getStatus().isCharging()?STATUS_CHARGING:STATUS_UNCHARGING);
        statusData.setSpeed((byte)(car.getStatus().getSpeed() * 2.0));
        statusData.setSignal((byte)0);
        statusData.setGprs((byte)0);
        statusData.setDatetime(Datetime.now());
        statusData.setAcc(car.getStatus().isAcc()?STATUS_ACC_ON:STATUS_ACC_OFF);
        Frame f1 = Frame.from(StatusData.DIRECTIVE,car.getId(),statusData);
        channel.writeAndFlush(f1);

    }

    private void rent(Frame<Rent> frame) {
        ack(car.rent().result(),frame);
    }

    private void repay(Frame<Repay> frame) {
        ack(car.repay().result(),frame);
    }

    private void carControl(Frame<CarControl> frame) {
        boolean result = true;
        CarControl carControl = frame.getPayload();
        if(Objects.equals(carControl.getDoor(),CarControl.DOOR_UNLOCK)){
            result = result && car.unlock().result();
        }else if(Objects.equals(carControl.getDoor(),CarControl.DOOR_LOCK)){
            result = result && car.lock().result();
        }
        if(Objects.equals(carControl.getReady(),CarControl.READY_ON)){
            result = result && car.power().result();
        }
        if(Objects.equals(carControl.getReady(),CarControl.READY_OFF)){
            result = result && car.unpower().result();
        }
        ack(result,frame);
    }

    private void setStatus(Frame<SetStatus> frame) {
        ack(true,frame);
    }

    private void ack(boolean success,Frame frame){
        Ack ack = new Ack();
        ack.setReqXor(frame.getXor());
        ack.setReqDirective(frame.getDirective());
        ack.setCode((byte)(success?0x00:0x01));
        channel.writeAndFlush(Frame.from(Ack.DIRECTIVE,frame.getId(),ack));

        //应答以后，立即发送状态，维持在线
        pushStatus();
    }

    private Function<Integer, Object> directiveFunction = directive->{
        switch (directive.byteValue()){
            case Ack.DIRECTIVE:
                return new Frame<Ack>(){};
            case CarControl.DIRECTIVE:
                return new Frame<CarControl>(){};
            case Rent.DIRECTIVE:
                return new Frame<Rent>(){};
            case Repay.DIRECTIVE:
                return new Frame<Repay>(){};
            case SetStatus.DIRECTIVE:
                return new Frame<SetStatus>(){};
            case Time.Ack.DIRECTIVE:
                return new Frame<Time.Ack>(){};
            default:
                throw new IllegalArgumentException("not support directive "+String.format("%02x",directive));
        }
    };

    private void boot() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(context.getIdle(),context.getIdle(),context.getIdle()*2));
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024, Frame.LENGTH_FIELD_OFFSET,Frame.LENGTH_FIELD_LENGTH));
                    ch.pipeline().addLast(new XorChecker(Frame.XOR_INDEX));
                    ch.pipeline().addLast(new LengthFieldBasedFrameEncoder(Frame.LENGTH_FIELD_OFFSET,Frame.LENGTH_FIELD_LENGTH));
                    ch.pipeline().addLast(new DirectiveCodec(Frame.DIRECTIVE_OFFSET,Frame.DIRECTIVE_LENGTH,directiveFunction));
                    ch.pipeline().addLast(new MessageToMessageCodec<Frame,Frame>() {
                        @Override
                        protected void encode(ChannelHandlerContext channelHandlerContext, Frame frame, List<Object> list) throws Exception {
                            list.add(frame);
                        }

                        @Override
                        protected void decode(ChannelHandlerContext channelHandlerContext, Frame frame, List<Object> list) throws Exception {
                            list.add(frame);
                        }
                    });
                    ch.pipeline().addLast(new GenericObjectChannelInboundHandler<Frame<Ack>,Ack>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame<Ack> frame) throws Exception {
                        }
                    });
                    ch.pipeline().addLast(new GenericObjectChannelInboundHandler<Frame<Rent>,Rent>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame<Rent> frame) throws Exception {
                            rent(frame);
                        }
                    });
                    ch.pipeline().addLast(new GenericObjectChannelInboundHandler<Frame<Repay>,Repay>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame<Repay> frame) throws Exception {
                            repay(frame);
                        }
                    });
                    ch.pipeline().addLast(new GenericObjectChannelInboundHandler<Frame<CarControl>,CarControl>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame<CarControl> frame) throws Exception {
                            carControl(frame);
                        }
                    });
                    ch.pipeline().addLast(new GenericObjectChannelInboundHandler<Frame<SetStatus>,SetStatus>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame<SetStatus> frame) throws Exception {
                            setStatus(frame);
                        }
                    });
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            channel = b.connect(context.getHost(), context.getPort()).sync().channel();
            connected = true;
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            connected = false;
        }

    }

    class ClientHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if(evt instanceof IdleStateEvent){
                IdleStateEvent event = (IdleStateEvent) evt;
                if(Objects.equals(event.state(), IdleState.WRITER_IDLE)){
                    pushStatus();
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            pushStatus();
        }
    }

}
