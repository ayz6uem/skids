package com.ybyc.skids.info.service;

import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import com.ybyc.skids.info.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarManage carManage;

    public void init() {
        List<Car> cars = carRepository.findAll();
        log.info("init:{}",cars);
        cars.forEach(car -> {
            car.tbox(car.getType());
            carManage.active(car);
        });
    }
}
