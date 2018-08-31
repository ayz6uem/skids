package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryChargeStatusParam {

    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;

}
