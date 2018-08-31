package com.ybyc.skids.car.tbox.threadcn;

import com.ybyc.skids.car.core.Car;
import com.ybyc.skids.car.core.Status;
import com.ybyc.skids.car.info.dto.StatusInfoDTO;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadcnTboxRunnable extends Thread {

    Car car = new Car();


    public CountDownLatch countDownLatch;

    public NioEventLoopGroup workerGroup;

    private String host = "127.0.0.1";
    private int port = 9620;
    private int idle = 120;

    private String id;

    public ThreadcnTboxRunnable(String id, NioEventLoopGroup workerGroup, CountDownLatch countDownLatch){
        super();
        this.id = id;
        this.workerGroup = workerGroup;
        this.countDownLatch = countDownLatch;
    }

    public ThreadcnTboxRunnable(String id){
        super();
        this.id = id;
        this.workerGroup = new NioEventLoopGroup();
    }

    public void off(){
        car.off();
    }

    @Override
    public void run() {
        try{
            log.info("开始启动{}",id);
            StatusInfoDTO statusInfoDTO = new StatusInfoDTO();
            statusInfoDTO.setSoc(66d);
            statusInfoDTO.setOdo(5719d);
            statusInfoDTO.setAcc(0);
            statusInfoDTO.setSpeed(9d);
            statusInfoDTO.setLng(113.593911);
            statusInfoDTO.setLat(34.85972);

            car = new Car();
            car.setId(id);
            car.setCarSn(id);
            car.setCarNumber(id);
            Status status = car.getStatus();
            status.setSoc(statusInfoDTO.getSoc());
            status.setOdo(statusInfoDTO.getOdo().intValue());
            status.setAcc(Objects.equals(1,statusInfoDTO.getAcc()));
            status.setSpeed(statusInfoDTO.getSpeed());
            status.setLongitude(statusInfoDTO.getLng());
            status.setLatitude(statusInfoDTO.getLat());
            status.setReady(false);
            status.setCharging(false);
            status.setDoor(false);
            status.setLock(true);
            ThreadcnContext threadcnContext = new ThreadcnContext();
            threadcnContext.setHost(host);
            threadcnContext.setPort(port);
            threadcnContext.setIdle(idle);
            ThreadcnTbox tbox = new ThreadcnTbox(car,threadcnContext);
            tbox.setWorkerGroup(workerGroup);
            car.setTbox(tbox);

            car.on();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        if(countDownLatch!=null){
            countDownLatch.countDown();
        }
    }
}
