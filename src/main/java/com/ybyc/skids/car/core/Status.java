package com.ybyc.skids.car.core;

import lombok.Data;

@Data
public class Status {

    private double soc;
    private double odo;
    private boolean acc;
    private double speed;
    private double longitude;
    private double latitude;
    private boolean ready;
    private boolean charging;
    private boolean door;
    private boolean lock;

    public void incOdo(double odo){
        this.odo += odo;
    }

    public void incSoc(double soc){
        this.soc += soc;
        if(this.soc>100){
            this.soc = 100;
        }
    }

    public void decSoc(double soc){
        this.soc -= soc;
        if(this.soc<0){
            this.soc = 0;
        }
    }

}
