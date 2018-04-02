package com.ybyc.simulator.car.info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CarDTO implements Serializable{
    private InfoDTO info;
    @JsonProperty("status")
    private StatusInfoDTO statusInfo;
}
