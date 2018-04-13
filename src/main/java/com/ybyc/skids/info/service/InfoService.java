package com.ybyc.skids.info.service;

import com.ybyc.skids.common.helper.Result;
import com.ybyc.skids.info.dto.CarDTO;
import com.ybyc.skids.info.dto.InfoDTO;
import com.ybyc.skids.info.dto.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoService {

    @Autowired
    RestTemplate restTemplate;

    public String URL_CAR = "/car?carSn={0}";
    public String URL_CAR_NUMBER = "/cars/info?carNumberOrVin={0}";

    @Autowired
    public void fill(@Value("${service.car.uri:}") String uri){
        URL_CAR = uri + URL_CAR;
        URL_CAR_NUMBER = uri + URL_CAR_NUMBER;
    }

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

    public Result<PageData<InfoDTO>> getByCarNumber(String carNumber){
        try{
            ResponseEntity<Result<PageData<InfoDTO>>> resp = restTemplate.exchange(URL_CAR_NUMBER, HttpMethod.GET,null,
                    new ParameterizedTypeReference<Result<PageData<InfoDTO>>>(){},
                    carNumber);
            return resp.getBody();
        }catch (Exception e){
            return Result.fail("获取车辆信息异常");
        }
    }

}
