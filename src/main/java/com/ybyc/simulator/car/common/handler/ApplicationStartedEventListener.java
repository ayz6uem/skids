package com.ybyc.simulator.car.common.handler;

import com.ybyc.simulator.car.info.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    CarService carService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        carService.init();
    }
}
