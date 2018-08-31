package com.ybyc.skids.charge.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.ybyc.skids.charge.param.QueryEquipAuthParam;
import lombok.Data;

/**
 * 请求设备认证返回结果
 *
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class EquipAuthResult {
    /**
     * 设备认证流水号
     */
    @JsonProperty("EquipAuthSeq")
    private String equipAuthSeq;
    /**
     * 充电设备接口编码
     */
    @JsonProperty("ConnectorID")
    private String connectorID;
    /**
     * 操作结果
     */
    @JsonProperty("SuccStat")
    private Integer succStat = 0;
    /**
     * 失败原因
     */
    @JsonProperty("FailReason")
    private Integer failReason = 0;

    public static EquipAuthResult of(QueryEquipAuthParam param){
        EquipAuthResult result = new EquipAuthResult();
        result.setEquipAuthSeq(param.getEquipAuthSeq());
        result.setConnectorID(param.getConnectorID());
        return result;
    }

}
