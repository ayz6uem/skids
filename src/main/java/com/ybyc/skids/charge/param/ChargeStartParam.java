package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChargeStartParam {

    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    @JsonProperty("ConnectorID")
    private String connectorID;

}
