package com.ybyc.skids.info.controller;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import com.ybyc.skids.info.dto.CarDTO;
import com.ybyc.skids.info.dto.StatusDTO;
import com.ybyc.skids.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/info")
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

    @GetMapping("/carNumber")
    public Result carNumber(String carNumber){
        return infoService.getByCarNumber(carNumber);
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
