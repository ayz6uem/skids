package com.ybyc.skids.car.direction.service;

import com.ybyc.skids.car.direction.dto.DrivingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;

@Slf4j
@Service
public class AmapService {

    public static DecimalFormat decimalFormat = new DecimalFormat(".000000");

    public static final String key = "68f09b284b85bd195e708aeb43b2407e";

    public static final String URL_DRIVING = "http://restapi.amap.com/v3/direction/driving"+
            "?key="+key+"&origin={1},{2}&destination={3},{4}";

    @Autowired
    RestTemplate restTemplate;

    public DrivingDTO driving(double lng1, double lat1, double lng2, double lat2){
        ResponseEntity<DrivingDTO> resp = restTemplate.exchange(URL_DRIVING, HttpMethod.GET,null,
                new ParameterizedTypeReference<DrivingDTO>(){},
                decimalFormat.format(lng1),decimalFormat.format(lat1),
                decimalFormat.format(lng2),decimalFormat.format(lat2));
        return resp.getBody();
    }

}
