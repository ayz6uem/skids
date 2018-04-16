package com.ybyc.skids.direction.controller;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import com.ybyc.skids.direction.domain.Route;
import com.ybyc.skids.direction.dto.DrivingDTO;
import com.ybyc.skids.direction.dto.LocationDTO;
import com.ybyc.skids.direction.service.AmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/direction")
public class DirectionController {

    @Autowired
    AmapService amapService;

    @Autowired
    CarManage carManage;

    @PostMapping("/{id}/to")
    public Result to(@PathVariable String id, @RequestBody LocationDTO locationDTO){
        Car car = carManage.get(id);
        DrivingDTO drivingDTO = amapService.driving(car.getStatus().getLongitude(),car.getStatus().getLatitude(),
                locationDTO.getLongitude(),locationDTO.getLatitude());
        if(drivingDTO.ok()){
            car.setRoute(drivingDTO.parseRoute());
            return Result.success();
        }else{
            return Result.fail(drivingDTO.getInfo());
        }
    }

}
