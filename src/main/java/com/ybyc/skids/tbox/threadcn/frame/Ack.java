package com.ybyc.skids.tbox.threadcn.frame;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ack implements Directive {

    public static final int TYPE_RENT = 3;
    public static final int TYPE_REPAY = 4;
    public static final int TYPE_MAINTAIN = 5;
    public static final int TYPE_UNMAINTAIN = 6;

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAIL = 205;
    public static final int CODE_TIMEOUT = 206;
    public static final int CODE_SPEED = 207;
    public static final int CODE_KEY = 210;
    public static final int CODE_DOOR = 211;

    public String getDirective(){
        return "36";
    }

    private String datetime;
    private int type;
    private int code;

    public static Ack rent(int code){
        return from(TYPE_RENT,code);
    }

    public static Ack repay(int code){
        return from(TYPE_REPAY,code);
    }

    public static Ack maintain(int code){
        return from(TYPE_MAINTAIN,code);
    }

    public static Ack unmaintain(int code){
        return from(TYPE_UNMAINTAIN,code);
    }
    public static Ack from(int type, int code){
        Ack ack = new Ack();
        ack.datetime = LocalDateTime.now().format(Frame.TIME_FORMATTER);
        ack.type = type;
        ack.code = code;
        return ack;
    }

}
