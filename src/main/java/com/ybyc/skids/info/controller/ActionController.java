package com.ybyc.skids.info.controller;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/action")
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
        return carManage.get(id).open();
    }

    @GetMapping("/{id}/close")
    public Result close(@PathVariable String id){
        return carManage.get(id).close();
    }

    @GetMapping("/{id}/startup")
    public Result startup(@PathVariable String id){
        return carManage.get(id).startup();
    }

    @GetMapping("/{id}/flameout")
    public Result flameout(@PathVariable String id){
        return carManage.get(id).flameout();
    }

    @GetMapping("/{id}/charging")
    public Result charging(@PathVariable String id){
        return carManage.get(id).charging();
    }

    @GetMapping("/{id}/uncharging")
    public Result uncharging(@PathVariable String id){
        return carManage.get(id).uncharging();
    }

    @GetMapping("/{id}/move")
    public Result move(@PathVariable String id,
                       @RequestParam(defaultValue = "0") double speed,
                       @RequestParam(defaultValue = "-1") double longitude,
                       @RequestParam(defaultValue = "-1") double latitude){
        return carManage.get(id).move(speed, longitude, latitude);
    }
    @GetMapping("/{id}/status/push")
    public Result pushStatus(@PathVariable String id){
        carManage.get(id).getTbox().pushStatus();
        return Result.success();
    }

}
