package com.ybyc.skids.tbox.threadcn;

import com.ybyc.skids.core.Car;
import com.ybyc.skids.info.dto.StatusInfoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadcnTboxTest {

    @Test
    public void test1(){
        StatusInfoDTO statusInfoDTO = new StatusInfoDTO();
        statusInfoDTO.setSoc(66d);
        statusInfoDTO.setOdo(5719d);
        statusInfoDTO.setAcc(0);
        statusInfoDTO.setSpeed(9d);
        statusInfoDTO.setLng(113.593911);
        statusInfoDTO.setLat(34.85972);

        Car car = Car.builder("E61721B90852", "E61721B90852", "E61721B90852")
                .tbox("srdtbox")
                .status(statusInfoDTO)
                .build();
        car.on();
    }

}
