package com.ybyc.skids.tbox.hikvision;

import com.ybyc.skids.core.BaseTbox;
import com.ybyc.skids.core.Car;
import com.ybyc.skids.core.TboxFactory;
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
