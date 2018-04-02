package com.ybyc.simulator.car.hikvision.frame;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = "code")
public class Ack {

    public static final byte DIRECTIVE = (byte)0xEE;

    private byte reqXor;
    private byte reqDirective;
    private byte code;

}
