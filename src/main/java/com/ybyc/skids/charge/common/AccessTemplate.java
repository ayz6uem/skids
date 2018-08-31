package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.ChargeService;
import com.ybyc.skids.charge.common.model.*;
import com.ybyc.skids.charge.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class AccessTemplate {

    @Resource
    RestTemplate restTemplate;

    @Resource
    Operator operator;

    @Resource
    ChargeService chargeService;

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

        HttpHeaders headers = new HttpHeaders();
        AccessToken accessToken = AccessTokenContext.get(Operator.OPERATOR_ID);
        if(accessToken!=null){
            headers.set("Authorization","Bearer "+accessToken.getAccessToken());
        }
        HttpEntity<EncryptRequestData> httpEntity = new HttpEntity<>(encryptRequestData,headers);

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, httpEntity, byte[].class);
        String result = new String(responseEntity.getBody(), StandardCharsets.UTF_8);
        log.info("url:{} operatorId:{} data:{} result:{}",url,operatorId,dataJson,result);
        EncryptResponseData encryptResponseData =JsonHelper.toObject(result,EncryptResponseData.class);

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
