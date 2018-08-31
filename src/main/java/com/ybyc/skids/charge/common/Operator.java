package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.helper.AesCodec;
import com.ybyc.skids.charge.helper.SignHelper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Operator {

    /**
     * 己方运营商ID
     */
    public static final String PLATFORM_OPERATOR_ID = "MA3XAFGU5";
    public static final String PLATFORM_OPERATOR_SECRET = "8PEU6wXTYhcRSy3o";
    public static final String PLATFORM_BASE_URL = "http://hlht.hechongdian.cn/exchange/charging/push";

    public static final String OPERATOR_ID = "skids";
    public static final String OPERATOR_SECRET = "8PEU6wXTYhcRSy3o";
    public static final String SIGN_SECRET = "w5ZFtcGp";
    public static final String DATA_SECRET = "W9vfNbV6WjfooSAQ";
    public static final String DATA_SECRET_IV = "2PFQt0Sx22rFiREi";

    public static String URL_QUERY_TOKEN = PLATFORM_BASE_URL+"/query_token";
    public static String URL_CHARGE_START_NOTIFY = PLATFORM_BASE_URL+"/notification_start_charge_result";
    public static String URL_CHARGE_STOP_NOTIFY = PLATFORM_BASE_URL+"/notification_stop_charge_result";
    public static String URL_CHARGE_STATUS_NOTIFY = PLATFORM_BASE_URL+"/notification_equip_charge_status";
    public static String URL_CHARGE_INFO_NOTIFY = PLATFORM_BASE_URL+"/notification_charge_order_info";
    public static String URL_CHARGE_RECORD_NOTIFY = PLATFORM_BASE_URL+"/notification_charge_order_record";
    public static String URL_STATION_STATUS_NOTIFY = PLATFORM_BASE_URL+"/notification_stationStatus";



    public static SignHelper signHelper = new SignHelper(SIGN_SECRET);

    public static AesCodec aesCodec = new AesCodec(Operator.DATA_SECRET, Operator.DATA_SECRET_IV);

    public boolean checkPlatform(String operatorId, String operatorSecret){
        return Objects.equals(operatorId,PLATFORM_OPERATOR_ID) && Objects.equals(operatorSecret,PLATFORM_OPERATOR_SECRET);
    }

    public boolean isPlatform(String operatorId){
        return Objects.equals(operatorId,PLATFORM_OPERATOR_ID);
    }

    public SignHelper getSignHelper() {
        return signHelper;
    }

    public AesCodec getAesCodec() {
        return aesCodec;
    }


}
