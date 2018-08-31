package com.ybyc.skids.charge.common.model;

/**
 * 第三方平台充电订单状态
 *
 * @author Aiqing
 * @date 2017/11/4
 */
public enum StartChargeSeqStatEnum {

    STARTING(1, "启动中"),
    CHARGING(2, "充电中"),
    STOPING(3, "停止中"),
    END(4, "已结束"),
    UNKNOW(5, "未知");

    private int code;
    private String text;

    StartChargeSeqStatEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static StartChargeSeqStatEnum fromCode(int code) {
        for (StartChargeSeqStatEnum statEnum : StartChargeSeqStatEnum.values()) {
            if (statEnum.getCode() == code) {
                return statEnum;
            }
        }
        return UNKNOW;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
