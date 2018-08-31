package com.ybyc.skids.charge.common.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Order {

    private String sn;
    private String connectorId;

    private StartChargeSeqStatEnum statEnum;
    private ConnectorStatusEnum connectorStatusEnum;

    private String startTime;
    private String endTime;

    private double current = 12.6;
    private double voltage = 220;
    private double power = 0;
    private double money = 0;

    public void pulse(){
        power += 1;
        money = power * 1.5;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(obj instanceof Order){
            Order other = (Order)obj;
            return Objects.equals(this.sn,other.sn);
        }
        return super.equals(obj);
    }
}
