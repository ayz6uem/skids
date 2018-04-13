package com.ybyc.skids.tbox.hikvision.frame;

import com.ybyc.gateway.nettyplus.core.option.Option;
import lombok.Data;

@Data
public class Rent {

    public static final byte DIRECTIVE = 0x33;

    private int card;
    @Option(value = 6)
    private String pin;

}
