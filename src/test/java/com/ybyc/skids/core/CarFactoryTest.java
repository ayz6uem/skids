package com.ybyc.skids.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarFactoryTest {

    @Test
    public void test1(){
//        Car car = Car.builder("13934437547","豫A11223").tbox("hiktbox").build();
        Car car = Car.builder("E61720390762","皖AY035B").tbox("srdtbox").build();

        Status status = car.getStatus();
        status.setSoc(39);
        status.setOdo(2900);
        status.setAcc(false);
        status.setSpeed(0);
        status.setLongitude(113.595440);
        status.setLatitude(34.796625);
        status.setReady(true);
        status.setCharging(false);
        status.setDoor(false);
        status.setLock(true);

        car.on();
    }

}
