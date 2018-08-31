package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备状态变化推送model
 *
 * @author Aiqing
 * @date 2017/12/2
 */
@Getter
@Setter
public class StationStatusNotify {

    @JsonProperty("ConnectorStatusInfo")
    private ConnectorStatusInfo connectorStatusInfo;
}
