package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryEquipAuthParam {

    @JsonProperty("EquipAuthSeq")
    String equipAuthSeq;
    @JsonProperty("ConnectorID")
    String connectorID;

}
