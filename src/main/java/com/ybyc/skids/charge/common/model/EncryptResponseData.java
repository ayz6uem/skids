package com.ybyc.skids.charge.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 参数返回
 *
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class EncryptResponseData {

    //参数编码
    @JsonProperty("Ret")
    private Integer ret;
    //提示信息
    @JsonProperty("Msg")
    private String msg;
    //参数内容
    @JsonProperty("Data")
    private String data;
    //签名
    @JsonProperty("Sig")
    private String sig;

}
