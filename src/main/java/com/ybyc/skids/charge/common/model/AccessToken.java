package com.ybyc.skids.charge.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Random;

/**
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class AccessToken {

    @JsonProperty("OperatorID")
    private String operatorID;
    //成功状态
    @JsonProperty("SuccStat")
    private Integer succStat;
    //获取的凭证
    @JsonProperty("AccessToken")
    private String accessToken;
    //凭证有效期
    @JsonProperty("TokenAvailableTime")
    private Integer tokenAvailableTime;
    //失败原因， 0无，1无此运营商 2密钥错误
    @JsonProperty("FailReason")
    private Integer failReason;

    public static AccessToken build(String operatorId){
        AccessToken accessToken = new AccessToken();
        accessToken.genericToken();
        accessToken.setOperatorID(operatorId);
        accessToken.setFailReason(0);
        accessToken.setSuccStat(0);
        //有效期24小时
        Integer expireTime = 24 * 60 * 60;
        accessToken.setTokenAvailableTime(expireTime);
        return accessToken;
    }

    public void genericToken(){
        //签发密钥， 采用64位随机数
        String pattern = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLOMNOPQRSTUVWXYZ";
        StringBuilder token = new StringBuilder();
        for (int i = 1; i <= 64; i++) {
            token.append(pattern.charAt(new Random().nextInt(62)));
        }
        this.accessToken = token.toString();
    }

}
