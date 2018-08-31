package com.ybyc.skids.car.tbox.hikvision.frame;

import com.ybyc.gateway.nettyplus.core.option.Option;
import com.ybyc.gateway.nettyplus.core.option.StringOption;
import lombok.Data;

public class Time {

    @Data
    public static class Req{
        public static final byte DIRECTIVE = 0x01;

        @Option(value = 2,string = StringOption.HEX)
        private String version;
        @Option(value = 2,string = StringOption.HEX)
        private String hardware;

    }

    @Data
    public static class Ack{
        public static final byte DIRECTIVE = 0x11;

        private Datetime datetime;
    }

}
