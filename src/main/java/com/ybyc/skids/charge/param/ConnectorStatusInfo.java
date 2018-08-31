package com.ybyc.skids.charge.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Aiqing
 * @date 2017/11/9
 */
@Getter
@Setter
public class ConnectorStatusInfo {

    /**
     * 充电设备接口编码
     */
    @JsonProperty("ConnectorID")
    private String connectorID;
    /**
     * 充电设备接口状态
     */
    @JsonProperty("Status")
    private Integer status;
    /**
     * 车位状态
     */
    @JsonProperty("ParkStatus")
    private Integer parkStatus = 0;
    /**
     * 地锁状态
     */
    @JsonProperty("LockStatus")
    private Integer lockStatus = 0;

}
