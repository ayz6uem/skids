package com.ybyc.simulator.car.tbox.threadcn;

import com.ybyc.simulator.car.core.BaseTbox;
import com.ybyc.simulator.car.core.Car;
import com.ybyc.simulator.car.core.TboxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srdtboxFactory")
public class ThreadcnFactory implements TboxFactory {

    @Autowired
    ThreadcnContext context;

    @Override
    public BaseTbox build(Car car) {
        return new ThreadcnTbox(car,context);
    }

}
