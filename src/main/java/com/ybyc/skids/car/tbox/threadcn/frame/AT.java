package com.ybyc.skids.car.tbox.threadcn.frame;

public class AT {

    public static final String RENT = "AT+SCVBR";
    public static final String REPAY = "AT+SCVRT";
    public static final String MAINTAIN = "AT+SCVOP";
    public static final String UNMAINTAIN = "AT+SCVCOP";
    public static final String LOCK = "AT+C81";
    public static final String UNLOCK = "AT+C01";
    public static final String FIND = "AT+C06";
    public static final String POWER = "AT+SCENG=1";
    public static final String UNPOWER = "AT+SCENG=0";
    public static final String RENTABLE = "AT+SCSTAT=301";
    public static final String FAULT = "AT+SCSTAT=304";

}
