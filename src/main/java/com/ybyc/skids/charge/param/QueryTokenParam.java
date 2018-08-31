package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueryTokenParam {

    @JsonProperty("OperatorID")
    String operatorId;
    @JsonProperty("OperatorSecret")
    String operatorSecret;

}
