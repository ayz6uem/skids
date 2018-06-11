package com.ybyc.skids.tbox.threadcn;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.common.location.Location;
import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.tbox.threadcn.frame.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ThreadcnTbox extends BaseTbox {

    ThreadcnContext context;

    Channel channel;

    EventLoopGroup workerGroup;

    public ThreadcnTbox(Car car, ThreadcnContext context) {
        super(car);
        this.context = context;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
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
            throw new IllegalArgumentException("设备正在连接");
        }
        StatusData statusData = new StatusData();
        statusData.setAcc(car.getStatus().isAcc()?StatusData.ACC_ON:StatusData.ACC_OFF);
        statusData.setDoor(car.getStatus().isDoor()?StatusData.DOOR_OPEN:StatusData.DOOR_CLOSE);
        statusData.setSpeed((int)car.getStatus().getSpeed());
        statusData.setOdo((int)car.getStatus().getOdo());
        statusData.setSoc(car.getStatus().getSoc());
        statusData.setRemainMileage((int)(car.getStatus().getSoc()*1.5));
        statusData.setCharging(car.getStatus().isCharging()?StatusData.CHARGING:StatusData.UNCHARGING);

        Location.Point point = new Location(car.getStatus().getLongitude(),car.getStatus().getLatitude()).toWgs84();

        statusData.setLongitude(point.lng);
        statusData.setLatitude(point.lat);

        channel.writeAndFlush(new Frame(car.getId(),statusData).toString());
    }

    public void pushInfo() {
        if(Objects.isNull(channel)){
            throw new IllegalArgumentException("设备正在连接");
        }
        Info info = new Info();
        info.setSn(car.getId());

        channel.writeAndFlush(new Frame(car.getId(),info).toString());
    }

    private void rent(){
        int code = car.rent().result()? Ack.CODE_SUCCESS: Ack.CODE_FAIL;
        channel.writeAndFlush(new Frame(car.getId(),Ack.rent(code)).toString());
    }

    private void repay(){
        String repayCode = car.repay().getCode();
        int code;
        if(Objects.equals(Result.OK_CODE,repayCode)){
            code = Ack.CODE_SUCCESS;
        }else if(Objects.equals(Result.CODE_DOOR,repayCode)){
            code = Ack.CODE_DOOR;
        }else if(Objects.equals(Result.CODE_SPEED,repayCode)){
            code = Ack.CODE_SPEED;
        }else{
            code = Ack.CODE_FAIL;
        }
        channel.writeAndFlush(new Frame(car.getId(),Ack.repay(code)).toString());
    }

    private void maintain(){
        int code = car.rent().result()? Ack.CODE_SUCCESS: Ack.CODE_FAIL;
        channel.writeAndFlush(new Frame(car.getId(),Ack.maintain(code)).toString());
    }

    private void unmaintain(){
        String repayCode = car.repay().getCode();
        int code;
        if(Objects.equals(Result.OK_CODE,repayCode)){
            code = Ack.CODE_SUCCESS;
        }else if(Objects.equals(Result.CODE_DOOR,repayCode)){
            code = Ack.CODE_DOOR;
        }else if(Objects.equals(Result.CODE_SPEED,repayCode)){
            code = Ack.CODE_SPEED;
        }else{
            code = Ack.CODE_FAIL;
        }
        channel.writeAndFlush(new Frame(car.getId(),Ack.unmaintain(code)).toString());
    }

    private void lock(){
        String lockCode = car.lock().getCode();
        int code;
        if(Objects.equals(Result.CODE_DOOR,lockCode)){
            code = ControlAck.CODE_DOOR;
        }else{
            code = ControlAck.CODE_SUCCESS;
        }
        channel.writeAndFlush(new Frame(car.getId(),ControlAck.lock(code)).toString());
    }

    private void unlock(){
        car.unlock();
        channel.writeAndFlush(new Frame(car.getId(),ControlAck.unlock(ControlAck.CODE_SUCCESS)).toString());
    }

    private void find(){
        car.find();
        channel.writeAndFlush(new Frame(car.getId(),ControlAck.find(ControlAck.CODE_SUCCESS)).toString());
    }

    private void power(){
        car.power();
        channel.writeAndFlush(new Frame(car.getId(),new PowerAck()).toString());
    }

    private void unpower(){
        car.unpower();
        channel.writeAndFlush(new Frame(car.getId(),new PowerAck()).toString());
    }

    private void rentable(){
        channel.writeAndFlush(new Frame(car.getId(),new StateAck()).toString());
    }

    private void fault(){
        channel.writeAndFlush(new Frame(car.getId(),new StateAck()).toString());
    }

    private void boot() {

        if(workerGroup==null){
            workerGroup = new NioEventLoopGroup();
        }
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ExceptionHandler());
                    ch.pipeline().addLast(new IdleStateHandler(context.getIdle(),context.getIdle(),context.getIdle()*2));
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            channel = b.connect(context.getHost(), context.getPort()).sync().channel();
            connected = true;
            log.info("{}启动完成",car.getCarNumber());
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            connected = false;
        }

    }

    class ExceptionHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error(cause.getMessage(),cause);
        }
    }

    class ClientHandler extends SimpleChannelInboundHandler<String> {

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
            if(channel==null){
                channel = ctx.channel();
            }
            pushInfo();
            pushStatus();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String cmd) throws Exception {
            if(cmd.startsWith(AT.RENT)){
                rent();
            }else if(cmd.startsWith(AT.REPAY)){
                repay();
            }else if(cmd.startsWith(AT.MAINTAIN)){
                maintain();
            }else if(cmd.startsWith(AT.UNMAINTAIN)){
                unmaintain();
            }else if(cmd.startsWith(AT.LOCK)){
                lock();
            }else if(cmd.startsWith(AT.UNLOCK)){
                unlock();
            }else if(cmd.startsWith(AT.FIND)){
                find();
            }else if(cmd.startsWith(AT.POWER)){
                power();
            }else if(cmd.startsWith(AT.UNPOWER)){
                unpower();
            }else if(cmd.startsWith(AT.RENTABLE)){
                rentable();
            }else if(cmd.startsWith(AT.FAULT)){
                fault();
            }
        }
    }

}
