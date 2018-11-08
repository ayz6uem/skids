package com.ybyc.skids.charge.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 充电订单信息
 *
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class ChargeOrderInfo {
    //充电订单号
    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    //接口编码
    @JsonProperty("ConnectorID")
    private String connectorID;
    //开始充电时间
    @JsonProperty("StartTime")
    private String startTime;
    //结束充电时间
    @JsonProperty("EndTime")
    private String endTime;
    //累计充电量
    @JsonProperty("TotalPower")
    private Double totalPower;
    //总电费
    @JsonProperty("TotalElecMoney")
    private Double totalElecMoney;
    //总服务费
    @JsonProperty("TotalSeviceMoney")
    private Double totalSeviceMoney;
    //累计总金额
    @JsonProperty("TotalMoney")
    private Double totalMoney;
    //充电结束原因
    @JsonProperty("StopReason")
    private Integer stopReason;
    @JsonProperty("SumPeriod")
    private Integer sumPeriod;

    public static ChargeOrderInfo of(Order order){
        ChargeOrderInfo orderInfo = new ChargeOrderInfo();
        orderInfo.setStartChargeSeq(order.getSn());
        orderInfo.setConnectorID(order.getConnectorId());
        orderInfo.setStartTime(order.getStartTime());
        orderInfo.setEndTime(order.getEndTime());
        orderInfo.setTotalPower(order.getPower().doubleValue());
        orderInfo.setTotalElecMoney(order.getMoney().doubleValue());
        orderInfo.setTotalSeviceMoney(order.getMoney().doubleValue());
        orderInfo.setTotalMoney(order.getMoney().doubleValue());
        orderInfo.setStopReason(0);
        orderInfo.setSumPeriod(0);
        return orderInfo;
    }

}
