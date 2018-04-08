package com.ybyc.simulator.car.info.web;

import com.ybyc.simulator.car.common.helper.BeanHelper;
import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.CarManage;
import com.ybyc.simulator.car.core.Status;
import com.ybyc.simulator.car.info.dto.CarDTO;
import com.ybyc.simulator.car.info.dto.StatusDTO;
import com.ybyc.simulator.car.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/car/info")
public class CarController {

    @Autowired
    InfoService infoService;

    @Autowired
    CarManage carManage;

    @GetMapping("/active")
    public Result active(String carSn){
        Result<CarDTO> infoResult = infoService.get(carSn);
        if(infoResult.isOk()){
            Result<Car> carResult = carManage.build(infoResult.getData());
            if(carResult.isOk()){
                carManage.active(carResult.getData());
            }
            return carResult;
        }
        return Result.fail(infoResult.getMsg());
    }

    @GetMapping("/{id}/unactive")
    public Result unactive(@PathVariable String id){
        carManage.unactive(id);
        return Result.success();
    }

    @GetMapping
    public Result list(){
        Collection<Car> carList = carManage.list();
        return Result.success(carList);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable String id){
        Car car = carManage.get(id);
        return Result.success(car);
    }

    @PostMapping
    public Result update(@RequestBody StatusDTO statusDTO){
        Car car = carManage.updateStatus(statusDTO);
        return Result.success(car);
    }

}
