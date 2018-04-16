package com.ybyc.skids.common.handler;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.CarManage;
import com.ybyc.skids.info.dto.CarDTO;
import com.ybyc.skids.info.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    CarManage carManage;

    @Autowired
    InfoService infoService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        List<Car> cars = carManage.findAll();
        log.info("init:{}",cars);
        cars.forEach(car -> {
            Result<CarDTO> result = infoService.get(car.getCarSn());
            log.info("result:{}",result);
            if(result.result()){
                CarDTO dto = result.getData();
                car.builder().tbox(car.getType()).status(dto.getStatusInfo());
                carManage.active(car);
            }
        });
    }
}
