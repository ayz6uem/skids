package com.ybyc.skids.car.tbox.hikvision.frame;

import com.ybyc.gateway.nettyplus.core.option.Option;
import com.ybyc.gateway.nettyplus.core.option.StringOption;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id","payload"})
public class Frame<T> {

    public static final int LENGTH_FIELD_OFFSET = 3;
    public static final int LENGTH_FIELD_LENGTH = 2;

    public static final int DIRECTIVE_OFFSET = 2;
    public static final int DIRECTIVE_LENGTH = 1;

    public static final int XOR_INDEX = -2;

    private short head = 0x2929;
    private byte directive;
    private short length;
    @Option(value = 6,string = StringOption.HEX)
    private String id;
    private T payload;
    private byte xor;
    private byte end = 0x0d;

    public static <T> Frame<T> from(byte directive,String id, T t){
        Frame<T> frame = new Frame<>();
        frame.directive = directive;
        frame.id = id;
        frame.payload = t;
        return frame;
    }

}
