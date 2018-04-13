package com.ybyc.skids.tbox.threadcn.frame;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ControlAck implements Directive{

    public static final String TYPE_UNLOCK = "C01";
    public static final String TYPE_LOCK = "C81";
    public static final String TYPE_FIND = "C06";

    public static final int CODE_SUCCESS = 500;
    public static final int CODE_DOOR = 505;
    public static final int CODE_STARTUP = 506;

    public String getDirective(){
        return "33";
    }

    private String type;
    private int code;

    public static ControlAck lock(int code){
        return from(TYPE_LOCK,code);
    }

    public static ControlAck unlock(int code){
        return from(TYPE_UNLOCK,code);
    }

    public static ControlAck find(int code){
        return from(TYPE_FIND,code);
    }

    public static ControlAck from(String type, int code){
        ControlAck controlAck = new ControlAck();
        controlAck.type = type;
        controlAck.code = code;
        return controlAck;
    }

}
