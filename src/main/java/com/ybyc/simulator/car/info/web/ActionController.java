package com.ybyc.simulator.car.info.web;

import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.CarManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    CarManage carManage;

    @GetMapping("/{id}/on")
    public Result on(@PathVariable String id){
        Car car = carManage.get(id);
        carManage.on(car);
        return Result.success();
    }

    @GetMapping("/{id}/off")
    public Result off(@PathVariable String id){
        carManage.get(id).off();
        return Result.success();
    }

    @GetMapping("/{id}/open")
    public Result open(@PathVariable String id){
        carManage.get(id).open();
        return Result.success();
    }

    @GetMapping("/{id}/close")
    public Result close(@PathVariable String id){
        carManage.get(id).close();
        return Result.success();
    }

    @GetMapping("/{id}/startup")
    public Result startup(@PathVariable String id){
        carManage.get(id).startup();
        return Result.success();
    }

    @GetMapping("/{id}/flameout")
    public Result flameout(@PathVariable String id){
        carManage.get(id).flameout();
        return Result.success();
    }

    @GetMapping("/{id}/charging")
    public Result charging(@PathVariable String id){
        carManage.get(id).charging();
        return Result.success();
    }

    @GetMapping("/{id}/uncharging")
    public Result uncharging(@PathVariable String id){
        carManage.get(id).uncharging();
        return Result.success();
    }

    @GetMapping("/{id}/move")
    public Result move(@PathVariable String id,
                       @RequestParam(defaultValue = "0") double speed,
                       @RequestParam(defaultValue = "-1") double longitude,
                       @RequestParam(defaultValue = "-1") double latitude){
        carManage.get(id).move(speed, longitude, latitude);
        return Result.success();
    }

}
