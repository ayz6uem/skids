package com.ybyc.skids.tbox.threadcn;

import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.TboxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("srdtboxFactory")
public class ThreadcnFactory implements TboxFactory {

    @Autowired
    ThreadcnContext context;

    @Override
    public BaseTbox build(Car car) {
        Assert.notNull(car.getId(),"设备号错误");
        Assert.isTrue(car.getId().length()==12,"设备号应该是12位，形如：E61254B88755");
        return new ThreadcnTbox(car,context);
    }

}
