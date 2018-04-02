package com.ybyc.simulator.car.info.service;

import com.ybyc.simulator.car.common.helper.Result;
import com.ybyc.simulator.car.info.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoService {

    public String URL_CAR = "/car?carSn={0}";

    @Autowired
    public void fill(@Value("${service.car.uri:}") String uri){
        URL_CAR = uri + URL_CAR;
    }

    RestTemplate restTemplate = new RestTemplate();

    public Result<CarDTO> get(String carSn){
        try{
            ResponseEntity<Result<CarDTO>> resp = restTemplate.exchange(URL_CAR, HttpMethod.GET,null,
                    new ParameterizedTypeReference<Result<CarDTO>>(){},
                    carSn);
            return resp.getBody();
        }catch (Exception e){
            return Result.fail("获取车辆信息异常");
        }
    }

}
