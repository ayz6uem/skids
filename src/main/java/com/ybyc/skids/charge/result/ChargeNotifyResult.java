package com.ybyc.skids.charge.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChargeNotifyResult {

    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    @JsonProperty("SuccStat")
    private Integer succStat;
    @JsonProperty("FailReason")
    private Integer failReason;

}
