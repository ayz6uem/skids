package com.ybyc.simulator.car.threadcn.frame;

import lombok.Data;

@Data
public class StateAck implements Directive{

    public String getDirective(){
        return "3D";
    }

    private String code = "OK";

}
