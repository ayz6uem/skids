package com.ybyc.simulator.car.core;

import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.info.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarManage {

    Map<String,Car> pool = new ConcurrentHashMap<>();

    public Result<Car> build(CarDTO dto){
        Car car = Car.builder(dto.getInfo().getTboxSimNumber(),dto.getInfo().getCarNumber())
                .tbox(dto.getInfo().getTboxTypeCode())
                .status(dto.getStatusInfo())
                .build();
        pool.put(car.getId(),car);
        return Result.success(car);
    }

    @Async
    public void on(Car car){
        Assert.notNull(car,"车辆不存在");
        car.on();
    }

    public void off(Car car) {
        Assert.notNull(car,"车辆不存在");
        car.off();
    }

    public Collection<Car> list() {
        return pool.values();
    }

    public Car get(String id) {
        Car car = pool.get(id);
        Assert.notNull(car,"车辆不存在");
        return car;
    }
}
