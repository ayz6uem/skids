package com.ybyc.skids.car.core;

import com.ybyc.skids.common.helper.BeanHelper;
import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.car.info.dto.CarDTO;
import com.ybyc.skids.car.info.dto.StatusDTO;
import com.ybyc.skids.car.info.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CarManage {

    Map<String, Car> pool = new ConcurrentHashMap<>();

    @Autowired
    CarRepository carRepository;

    public Result<Car> build(CarDTO dto) {
        if (pool.containsKey(dto.getInfo().getTboxSimNumber())) {
            throw new IllegalArgumentException("车辆已激活，不允许重复激活");
        }
        Car car = Car.builder(dto.getInfo().getTboxSimNumber().trim(), dto.getInfo().getCarSn(), dto.getInfo().getCarNumber())
                .tbox(dto.getInfo().getTboxTypeCode())
                .status(dto.getStatusInfo())
                .build();
        carRepository.save(car);
        return Result.success(car);
    }

    @Async
    public void active(Car car) {
        Assert.notNull(car, "车辆不存在");
        pool.put(car.getId(), car);
        log.info("{}启动中",car.getCarNumber());
        car.on();
    }

    public Result unactive(String id) {
        Car car = get(id);
        car.off();
        pool.remove(id);
        carRepository.deleteById(id);
        return Result.success(car);
    }

    public void on(Car car) {
        Assert.notNull(car, "车辆不存在");
        car.on();
    }

    public void off(Car car) {
        Assert.notNull(car, "车辆不存在");
        car.off();
    }

    public Collection<Car> list() {
        return pool.values();
    }

    public Car get(String id) {
        Car car = pool.get(id);
        Assert.notNull(car, "车辆不存在");
        return car;
    }

    public Car updateStatus(StatusDTO statusDTO) {
        Car car = get(statusDTO.getId());
        car.setStatus(BeanHelper.map(statusDTO, Status.class));
        update(car);
        return car;
    }

    public Car update(Car car) {
        Assert.notNull(car, "车辆不存在");
        carRepository.save(car);
        return car;
    }

    public List<Car> findAll(){
        List<Car> cars = carRepository.findAll();
        return cars;
    }
}
