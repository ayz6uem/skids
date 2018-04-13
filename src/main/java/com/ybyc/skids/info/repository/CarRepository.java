package com.ybyc.skids.info.repository;

import com.ybyc.skids.core.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends MongoRepository<Car,String> {
}
