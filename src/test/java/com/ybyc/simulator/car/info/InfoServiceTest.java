package com.ybyc.simulator.car.info;

import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.info.dto.CarDTO;
import com.ybyc.simulator.car.info.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class InfoServiceTest {

    @Autowired
    InfoService infoService;

    @Test
    public void test1(){
        Result<CarDTO> result = infoService.get("2016zdd1201610754001");
        log.info("result:{}",result.getData());
    }

}
