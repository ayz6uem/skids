package com.ybyc.skids.charge.common.model;

import lombok.Data;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
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
    private BigDecimal power = new BigDecimal(0);
    private BigDecimal money = new BigDecimal(0);
    private BigDecimal serviceMoney = new BigDecimal(0);
    private BigDecimal totalMoney = new BigDecimal(0);

    public void pulse() {
        power = power.add(new BigDecimal(Math.random() + 0.5)).setScale(2, BigDecimal.ROUND_HALF_UP);
        money = power.multiply(new BigDecimal(1.5));
        serviceMoney = power.multiply(new BigDecimal(0.5));
        totalMoney = power.multiply(new BigDecimal(2));
}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Order) {
            Order other = (Order) obj;
            return Objects.equals(this.sn, other.sn);
        }
        return super.equals(obj);
    }
}
