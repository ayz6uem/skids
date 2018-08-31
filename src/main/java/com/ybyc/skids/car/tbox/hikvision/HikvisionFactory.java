package com.ybyc.skids.car.tbox.hikvision;

import com.ybyc.skids.car.core.BaseTbox;
import com.ybyc.skids.car.core.Car;
import com.ybyc.skids.car.core.TboxFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("hktboxFactory")
public class HikvisionFactory implements TboxFactory {

    @Autowired
    HikvisionContext hikvisionContext;

    @Override
    public BaseTbox build(Car car) {
        Assert.notNull(car.getId(),"设备号错误");
        Assert.isTrue(car.getId().length()==11,"设备号应该是11位");
        return new HikvisionTbox(car,hikvisionContext);
    }
}
