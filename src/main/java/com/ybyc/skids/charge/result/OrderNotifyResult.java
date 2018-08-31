package com.ybyc.skids.charge.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderNotifyResult {

    @JsonProperty("StartChargeSeq")
    private String startChargeSeq;
    @JsonProperty("ConnectorID")
    private String connectorID;
    @JsonProperty("ConfirmResult")
    private String confirmResult;

}
