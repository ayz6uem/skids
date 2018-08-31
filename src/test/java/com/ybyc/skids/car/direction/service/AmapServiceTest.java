package com.ybyc.skids.car.direction.service;

import com.ybyc.skids.car.direction.domain.Route;
import com.ybyc.skids.car.direction.dto.DrivingDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AmapServiceTest {

    @Autowired
    AmapService amapService;

    @Test
    public void test1(){
        DrivingDTO drivingDTO = amapService.driving(113.756481,34.568754,113.768425,34.968572);
        System.out.println(drivingDTO);
        if(drivingDTO.ok()){
            Route route = drivingDTO.parseRoute();
            System.out.println(route);
        }
    }

}
