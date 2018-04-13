package com.ybyc.skids.tbox.threadcn.frame;

import lombok.Data;

@Data
public class PowerAck implements Directive{

    public String getDirective(){
        return "3E";
    }

    private String code = "OK";

}
