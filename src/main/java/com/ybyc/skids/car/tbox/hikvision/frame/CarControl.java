package com.ybyc.skids.car.tbox.hikvision.frame;

import lombok.Data;

@Data
public class CarControl {

    public static final byte DIRECTIVE = 0x36;

    public static final byte DOOR_UNLOCK = 0x01;
    public static final byte DOOR_LOCK = 0x11;
    public static final byte READY_ON = 0x11;
    public static final byte READY_OFF = 0x11;

    private byte door;
    private byte ready;

}
