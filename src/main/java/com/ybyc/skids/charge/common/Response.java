package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.common.model.EncryptResponseData;
import com.ybyc.skids.charge.helper.JsonHelper;

import java.util.Objects;

public class Response {

    public static EncryptResponseData ok(Object data){
        return build(data,0,"success");
    }
    public static EncryptResponseData fail(int ret, String msg){
        return build(null,ret,msg);
    }
    public static EncryptResponseData fail(String msg){
        return build(null,1,msg);
    }
    public static EncryptResponseData fail(String msg, Object data){
        return build(data,1,msg);
    }
    public static EncryptResponseData build(Object data,Integer ret, String msg){
        EncryptResponseData encryptResponseData = new EncryptResponseData();
        if(Objects.nonNull(data)){
            String dataJson = JsonHelper.toJson(data);
            String encryptData = Operator.aesCodec.cbcEncrypt(dataJson);
            encryptResponseData.setData(encryptData);
        }
        encryptResponseData.setRet(ret);
        encryptResponseData.setMsg(msg);
        Operator.signHelper.sign(encryptResponseData);
        return encryptResponseData;
    }

}
