package com.ybyc.simulator.car.tbox.hikvision;

import com.ybyc.simulator.car.core.BaseTbox;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.TboxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hktboxFactory")
public class HikvisionFactory implements TboxFactory {

    @Autowired
    HikvisionContext hikvisionContext;

    @Override
    public BaseTbox build(Car car) {
        return new HikvisionTbox(car,hikvisionContext);
    }
}
