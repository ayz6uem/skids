package com.ybyc.skids.charge.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StationStatusResult {

    @JsonProperty("Status")
    private int status;

}
