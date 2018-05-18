package com.ybyc.skids.tbox.threadcn.frame;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Info implements Directive {

    public String getDirective(){
        return "81";
    }

    private String sn;
    private String hardware = "1";
    private String firmware = "1";
    private String iap = "1";
    private String plus = "1";
    private String plusIap = "1";
    private String mac = "1";
    private String iccid = "1";


}
