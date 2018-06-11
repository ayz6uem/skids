package com.ybyc.skids.tbox.threadcn;

import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.Status;
import com.ybyc.skids.info.dto.StatusInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ThreadcnTboxRunnable extends Thread {

    ThreadcnTboxBatchTest batchTest;

    private String host = "47.100.76.78";
    private int port = 8622;
    private int idle = 120;

    private String id;

    public ThreadcnTboxRunnable(String id, ThreadcnTboxBatchTest batchTest){
        super();
        this.id = id;
        this.batchTest = batchTest;
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

            Car car = new Car();
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
            tbox.setWorkerGroup(batchTest.workerGroup);
            car.setTbox(tbox);

            car.on();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        batchTest.countDownLatch.countDown();
    }
}
