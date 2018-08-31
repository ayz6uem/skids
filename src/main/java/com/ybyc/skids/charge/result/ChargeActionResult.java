package com.ybyc.skids.charge.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class ChargeActionResult {
    //充电订单号
    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    //充电订单状态
    @JsonProperty("StartChargeSeqStat")
    private Integer startChargeSeqStat;
    //充电设备接口编码
    @JsonProperty("ConnectorID")
    private String connectorID;
    //充电开始时间
    @JsonProperty("StartTime")
    private String startTime;
    //操作结果, 0成功， 1失败
    @JsonProperty("SuccStat")
    private Integer succStat = 0;
    //失败原因，0无， 1此设备不存在， 2 此设备离线，3-99 自定义
    @JsonProperty("FailReason")
    private Integer failReason = 0;

}
