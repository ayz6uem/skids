package com.ybyc.skids.car.tbox.threadcn.frame;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusData implements Directive {

    public static final int ACC_ON = 2;
    public static final int ACC_OFF = 1;

    public static final int DOOR_OPEN = 0;
    public static final int DOOR_CLOSE = 1;

    public static final int CHARGING = 1;
    public static final int UNCHARGING = 0;

    public String getDirective(){
        return "3A";
    }

    private String datetime = LocalDateTime.now().format(Frame.TIME_FORMATTER);
    private String preStatus = "301";
    private String status = "301";
    private String card = "-1";
    private int acc;
    private int door;
    private String gear = "3";
    private int speed;
    private int odo;
    private String voltage = "0";
    private String bms = "0";
    private String current = "0";
    private double soc;
    private int remainMileage;
    private int charging;
    private String signal = "31";
    private String location = "1";
    private String satellite = "0";
    private double longitude;
    private double latitude;


}
