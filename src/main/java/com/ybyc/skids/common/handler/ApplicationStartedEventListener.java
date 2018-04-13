package com.ybyc.skids.common.handler;

import com.ybyc.skids.info.service.CarService;
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
