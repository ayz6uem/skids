package com.ybyc.simulator.car.info.service;

import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.CarManage;
import com.ybyc.simulator.car.info.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarManage carManage;

    @PostConstruct
    public void init() {
        List<Car> cars = carRepository.findAll();
        log.info("init:{}",cars);
        cars.forEach(car -> {
            car.tbox(car.getType());
            carManage.active(car);
        });
    }
}
