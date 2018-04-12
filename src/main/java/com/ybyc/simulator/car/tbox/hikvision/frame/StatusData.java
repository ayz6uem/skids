package com.ybyc.simulator.car.tbox.hikvision.frame;

import com.ybyc.gateway.nettyplus.core.option.Option;
import lombok.Data;

@Data
public class StatusData {

    public static final byte DIRECTIVE = 0x07;

    private byte preStatus;
    private byte status;
    private int longitude;
    private int latitude;
    private byte soc;
    private int card;
    @Option(value = 3)
    private int odo;
    private short current;
    private byte charging;
    private byte speed;
    private byte signal;
    private byte gprs;
    private Datetime datetime;
    private byte acc;


}
