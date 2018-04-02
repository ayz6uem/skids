package com.ybyc.simulator.car.core;

import com.ybyc.simulator.car.common.handler.ApplicationContextHolder;
import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.info.dto.StatusInfoDTO;
import lombok.Data;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.Assert;

import java.util.Objects;

@Data
public class Car {

    /**
     * tbox sim number 作为车的主键
     */
    private String id;
    /**
     * 车牌号
     */
    private String carNumber;
    /**
     * 车机类型，通过哪种车机上报数据
     */
    private String type;

    private Status status;

    private BaseTbox tbox;

    /**
     * 注意要异步调用，该方法会阻塞。
     */
    public void on(){
        tbox.on();
    }

    public void off(){
        tbox.off();
    }


    public Result open(){
        if(status.isLock()){
            return Result.fail("门已上锁");
        }
        status.setDoor(true);
        return Result.success();
    }

    public Result close(){
        status.setDoor(false);
        return Result.success();
    }

    public Result startup(){
        if(status.isReady()){
            status.setAcc(true);
            return Result.success();
        }
        return Result.fail("没有动力");
    }

    public Result flameout(){
        status.setAcc(false);
        return Result.success();
    }

    public Result move(double speed, double longitude, double latitude){
        if(status.isAcc()){
            status.setSpeed(speed);
            status.setLongitude(longitude);
            status.setLatitude(latitude);
            return Result.success();
        }
        return Result.fail("未启动");
    }

    public Result charging(){
        status.setCharging(true);
        return Result.success();
    }

    public Result uncharging(){
        status.setCharging(false);
        return Result.success();
    }

    public Result rent(){
        status.setReady(true);
        return Result.success();
    }

    public Result repay(){
        if(status.getSpeed()>0){
            return Result.speed();
        }
        if(status.isAcc()){
            return Result.startup();
        }
        Result result = lock();
        if(!result.isOk()){
            return result;
        }
        result = unpower();
        return result;
    }

    public Result find(){
        return Result.success();
    }

    public Result lock(){
        if(status.isDoor()){
            return Result.door();
        }
        status.setLock(true);
        return Result.success();
    }

    public Result unlock(){
        status.setLock(false);
        return Result.success();
    }

    public Result power(){
        status.setReady(true);
        return Result.success();
    }

    public Result unpower(){
        status.setReady(false);
        return Result.success();
    }

    /**
     * 车辆构建
     * @param id
     * @param carNumber
     * @return
     */
    public static CarBuilder builder(String id, String carNumber){
        return new CarBuilder(id,carNumber);
    }

    /**
     * 车辆构建类
     */
    public static class CarBuilder {

        Car car;

        public CarBuilder(String id, String carNumber){
            car = new Car();
            car.setId(id);
            car.setCarNumber(carNumber);
        }

        public CarBuilder tbox(String type){
            Assert.notNull(car,"please call init first");
            try{
                car.setType(type);
                TboxFactory tboxFactory = ApplicationContextHolder.getBean(String.format("%sFactory",car.getType()),TboxFactory.class);
                car.setTbox(tboxFactory.build(car));
            }catch (NoSuchBeanDefinitionException e){
                throw new IllegalArgumentException("设备类型不支持:"+car.getType());
            }
            return this;
        }

        public CarBuilder status(StatusInfoDTO statusInfoDTO){
            Assert.notNull(car,"please call init first");
            Status status = new Status();
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
            car.setStatus(status);
            return this;
        }

        public Car build(){
            Assert.notNull(car,"please call init first");
            return car;
        }

    }

}
