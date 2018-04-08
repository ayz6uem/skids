package com.ybyc.simulator.car.info.repository;

import com.ybyc.simulator.car.core.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends MongoRepository<Car,String> {
}
