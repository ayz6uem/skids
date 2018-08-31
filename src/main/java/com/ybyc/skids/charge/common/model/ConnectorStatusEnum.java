package com.ybyc.skids.charge.common.model;

/**
 * 充电桩枪状态
 *
 * @author Aiqing
 * @date 2017/11/4
 */
public enum ConnectorStatusEnum {

    OFFLINE(0, "离线"),
    IDLE(1, "空闲"),
    USED_UNCHARGE(2, "占用未充电"),
    USED_CHARGING(3, "充电中"),
    USED_RESERVED(4, "占用-预约锁定"),
    PARKING(5, "待拔枪"),
    ERROR(255, "故障");

    private int code;
    private String text;

    ConnectorStatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static ConnectorStatusEnum fromCode(int code) {
        for (ConnectorStatusEnum connectorStatusEnum : ConnectorStatusEnum.values()) {
            if (connectorStatusEnum.getCode() == code) {
                return connectorStatusEnum;
            }
        }
        return null;
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
