package com.ybyc.skids.tbox.threadcn;

import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.TboxFactory;
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
