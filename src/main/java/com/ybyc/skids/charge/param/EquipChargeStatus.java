package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ybyc.skids.charge.common.model.Order;
import lombok.Getter;
import lombok.Setter;

/**
 * 充电状态
 *
 * @author Aiqing
 * @date 2017/11/8
 */
@Getter
@Setter
public class EquipChargeStatus {

    /**
     * 充点订单号
     */
    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    /**
     * 充电订单状态
     */
    @JsonProperty("StartChargeSeqStat")
    private Integer startChargeSeqStat;
    /**
     * 充电设备接口ID
     */
    @JsonProperty("ConnectorID")
    private String connectorID;
    /**
     * 充电设备接口状态
     */
    @JsonProperty("ConnectorStatus")
    private Integer connectorStatus;
    /**
     * 电流
     */
    @JsonProperty("CurrentA")
    private Double currentA;
    /**
     * 电压
     */
    @JsonProperty("VoltageA")
    private Double voltageA;
    /**
     * 电池剩余电量
     */
    @JsonProperty("Soc")
    private Double soc;
    /**
     * 开始充电时间
     */
    @JsonProperty("StartTime")
    private String startTime;
    /**
     * 本次采样时间
     */
    @JsonProperty("EndTime")
    private String endTime;
    /**
     * 累计充电量
     */
    @JsonProperty("TotalPower")
    private Double totalPower;
    /**
     * 累计电费
     */
    @JsonProperty("ElecMoney")
    private Double elecMoney;
    /**
     * 累计服务费
     */
    @JsonProperty("SeviceMoney")
    private Double seviceMoney;
    /**
     * 累计总金额
     */
    @JsonProperty("TotalMoney")
    private Double totalMoney;

    public static EquipChargeStatus of(Order order){

        EquipChargeStatus chargeStatus = new EquipChargeStatus();
        if(order!=null){
            chargeStatus.setStartChargeSeq(order.getSn());
            chargeStatus.setStartChargeSeqStat(order.getStatEnum().getCode());
            chargeStatus.setConnectorID(order.getConnectorId());
            chargeStatus.setConnectorStatus(order.getConnectorStatusEnum().getCode());
            chargeStatus.setCurrentA(order.getCurrent());
            chargeStatus.setVoltageA(order.getVoltage());
            chargeStatus.setSoc(0d);
            chargeStatus.setStartTime(order.getStartTime());
            chargeStatus.setEndTime(order.getEndTime());
            chargeStatus.setTotalPower(order.getPower().doubleValue());

            chargeStatus.setElecMoney(order.getMoney().doubleValue());
            chargeStatus.setSeviceMoney(order.getServiceMoney().doubleValue());
            chargeStatus.setTotalMoney(order.getTotalMoney().doubleValue());
        }

        return chargeStatus;
    }

}
