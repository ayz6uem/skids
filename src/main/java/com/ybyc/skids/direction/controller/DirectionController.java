package com.ybyc.skids.direction.controller;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import com.ybyc.skids.direction.domain.Route;
import com.ybyc.skids.direction.dto.DrivingDTO;
import com.ybyc.skids.direction.service.AmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direction")
public class DirectionController {

    @Autowired
    AmapService amapService;

    @Autowired
    CarManage carManage;

    @PostMapping("/{id}/to")
    public Result to(@PathVariable String id, double longitude, double latitude){
        Car car = carManage.get(id);
        DrivingDTO drivingDTO = amapService.driving(car.getStatus().getLongitude(),car.getStatus().getLatitude(),longitude,latitude);
        if(drivingDTO.ok()){
            car.setRoute(drivingDTO.parseRoute());
            return Result.success();
        }else{
            return Result.fail(drivingDTO.getInfo());
        }
    }

}
