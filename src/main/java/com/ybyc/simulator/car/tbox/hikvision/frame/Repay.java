package com.ybyc.simulator.car.tbox.hikvision.frame;

import lombok.Data;

@Data
public class Repay {

    public static final byte DIRECTIVE = 0x34;

    private int card;
    private byte type;

}
