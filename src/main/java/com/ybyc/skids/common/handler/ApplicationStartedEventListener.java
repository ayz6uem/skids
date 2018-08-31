package com.ybyc.skids.common.handler;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.car.core.Car;
import com.ybyc.skids.car.core.CarManage;
import com.ybyc.skids.car.info.dto.CarDTO;
import com.ybyc.skids.car.info.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${service.car.init:true}")
    boolean init;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        if(!init){
            return;
        }
        List<Car> cars = carManage.findAll();
        log.info("init:{}",cars);
        cars.forEach(car -> {
            Result<CarDTO> result = infoService.get(car.getCarSn());
            if(result.result()){
                CarDTO dto = result.getData();
                car.builder().tbox(car.getType()).status(dto.getStatusInfo());
                carManage.active(car);
            }
        });
    }
}
