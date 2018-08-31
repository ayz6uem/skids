package com.ybyc.skids.car.direction.domain;

import lombok.Data;

@Data
public class Step {

    private String instruction;
    /**
     * 单位 千米
     */
    private double distance;
    private int duration;
    private double speed;
    private double longitude;
    private double latitude;

}
