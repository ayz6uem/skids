package com.ybyc.skids.charge.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class EncryptRequestData {
    //运营商ID
    @JsonProperty("OperatorID")
    private String operatorID;
    @JsonProperty("Data")
    private String data;
    //时间戳
    @JsonProperty("TimeStamp")
    private String timeStamp;
    //自增序列
    @JsonProperty("Seq")
    private String seq = "0001";
    //参数签名
    @JsonProperty("Sig")
    private String sig;

}
