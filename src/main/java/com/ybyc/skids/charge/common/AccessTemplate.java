package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.common.model.EncryptRequestData;
import com.ybyc.skids.charge.common.model.EncryptResponseData;
import com.ybyc.skids.charge.common.model.RequestData;
import com.ybyc.skids.charge.common.model.ResponseData;
import com.ybyc.skids.charge.helper.JsonHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AccessTemplate {

    @Resource
    RestTemplate restTemplate;

    @Resource
    Operator operator;

    public <T> ResponseData<T> post(String url, RequestData requestData, Class<T> resultClass) {
        return post(url, requestData.getOperatorID(), requestData.getData(), resultClass);
    }

    public <T> ResponseData<T> post(String url, Object data, Class<T> resultClass) {
        return post(url, Operator.OPERATOR_ID, data, resultClass);
    }

    public <T> ResponseData<T> post(String url, String operatorId, Object data, Class<T> resultClass) {

        String dataJson = JsonHelper.toJson(data);
        String encryptData = operator.getAesCodec().cbcEncrypt(dataJson);

        EncryptRequestData encryptRequestData = new EncryptRequestData();
        encryptRequestData.setOperatorID(operatorId);
        encryptRequestData.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        encryptRequestData.setData(encryptData);
        operator.getSignHelper().sign(encryptRequestData);

        EncryptResponseData encryptResponseData = restTemplate.postForObject(url, encryptRequestData, EncryptResponseData.class);

        if (!operator.getSignHelper().check(encryptResponseData)) {
            throw new IllegalArgumentException("请求失败");
        }

        ResponseData<T> responseData = new ResponseData<>();
        responseData.setRet(encryptResponseData.getRet());
        responseData.setMsg(encryptResponseData.getMsg());
        responseData.setData(JsonHelper.toObject(operator.getAesCodec().cbcDecrypt(encryptResponseData.getData()), resultClass));
        return responseData;
    }

}
