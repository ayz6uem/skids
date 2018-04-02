package com.ybyc.simulator.car.hikvision.frame;

import lombok.Data;

@Data
public class SetStatus {

    public static final byte DIRECTIVE = 0x35;

    public static final byte STATUS_FAULT = 0x01;
    public static final byte STATUS_RENTABLE = 0x11;

    private byte status;

}
