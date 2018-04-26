package com.ybyc.skids.tbox.hikvision;

import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.TboxFactory;
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
