package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.common.model.EncryptResponseData;
import com.ybyc.skids.charge.helper.JsonHelper;

public class Response {

    public static EncryptResponseData ok(Object data){
        return build(data,0,"success");
    }
    public static EncryptResponseData fail(Object data){
        return build(data,1,"fail");
    }
    public static EncryptResponseData fail(String msg, Object data){
        return build(data,1,msg);
    }
    public static EncryptResponseData build(Object data,Integer ret, String msg){
        String dataJson = JsonHelper.toJson(data);
        String encryptData = Operator.aesCodec.cbcEncrypt(dataJson);
        EncryptResponseData encryptResponseData = new EncryptResponseData();
        encryptResponseData.setData(encryptData);
        encryptResponseData.setRet(ret);
        encryptResponseData.setMsg(msg);
        Operator.signHelper.sign(encryptResponseData);
        return encryptResponseData;
    }

}
