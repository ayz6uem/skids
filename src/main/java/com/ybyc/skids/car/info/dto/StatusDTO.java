package com.ybyc.skids.car.info.dto;

import lombok.Data;

@Data
public class StatusDTO {

    private String id;
    private double soc;
    private int odo;
    private boolean acc;
    private double speed;
    private double longitude;
    private double latitude;
    private boolean ready;
    private boolean charging;
    private boolean door;
    private boolean lock;

}
