package com.ybyc.skids.charge;

import com.ybyc.skids.charge.common.AccessTemplate;
import com.ybyc.skids.charge.common.Operator;
import com.ybyc.skids.charge.common.model.*;
import com.ybyc.skids.charge.param.ConnectorStatusInfo;
import com.ybyc.skids.charge.param.EquipChargeStatus;
import com.ybyc.skids.charge.param.QueryTokenParam;
import com.ybyc.skids.charge.param.StationStatusNotify;
import com.ybyc.skids.charge.result.ChargeActionResult;
import com.ybyc.skids.charge.result.ChargeNotifyResult;
import com.ybyc.skids.charge.result.OrderNotifyResult;
import com.ybyc.skids.charge.result.StationStatusResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChargeService {

    @Resource
    AccessTemplate accessTemplate;

    public AccessToken getAccessToken(){
        QueryTokenParam queryTokenParam = new QueryTokenParam();
        queryTokenParam.setOperatorId(Operator.OPERATOR_ID);
        queryTokenParam.setOperatorSecret(Operator.OPERATOR_SECRET);
        ResponseData<AccessToken> responseData = accessTemplate.post(Operator.URL_QUERY_TOKEN, queryTokenParam, AccessToken.class);
        if(!responseData.isOk()){
            throw new IllegalArgumentException(responseData.getMsg());
        }
        return responseData.getData();
    }

    public void chargeStartNotify(ChargeActionResult actionResult) {
        accessTemplate.post(Operator.URL_CHARGE_START_NOTIFY,actionResult, ChargeNotifyResult.class);
    }

    public void chargeStopNotify(ChargeActionResult actionResult) {
        accessTemplate.post(Operator.URL_CHARGE_STOP_NOTIFY,actionResult, ChargeNotifyResult.class);
    }

    public void chargeStatusNotify(Order order){
        EquipChargeStatus chargeStatus = EquipChargeStatus.of(order);
        accessTemplate.post(Operator.URL_CHARGE_STATUS_NOTIFY,chargeStatus,ChargeNotifyResult.class);
    }

    public void chargeInfoNotify(Order order){
        ChargeOrderInfo orderInfo = ChargeOrderInfo.of(order);
        accessTemplate.post(Operator.URL_CHARGE_INFO_NOTIFY,orderInfo,OrderNotifyResult.class);
    }

    public void chargeRecordNotify(Order order){
        ChargeOrderInfo orderInfo = ChargeOrderInfo.of(order);
        accessTemplate.post(Operator.URL_CHARGE_RECORD_NOTIFY,orderInfo,OrderNotifyResult.class);
    }

    public void stationStatusNotify(String connectorId, ConnectorStatusEnum connectorStatusEnum){
        StationStatusNotify stationStatusNotify = new StationStatusNotify();
        ConnectorStatusInfo connectorStatusInfo = new ConnectorStatusInfo();
        connectorStatusInfo.setConnectorID(connectorId);
        connectorStatusInfo.setStatus(connectorStatusEnum.getCode());
        stationStatusNotify.setConnectorStatusInfo(connectorStatusInfo);
        accessTemplate.post(Operator.URL_STATION_STATUS_NOTIFY,stationStatusNotify,StationStatusResult.class);
    }

}
