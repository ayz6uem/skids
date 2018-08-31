package com.ybyc.skids.charge;

import com.ybyc.skids.charge.common.*;
import com.ybyc.skids.charge.common.model.AccessToken;
import com.ybyc.skids.charge.common.model.Order;
import com.ybyc.skids.charge.common.model.RequestData;
import com.ybyc.skids.charge.common.model.StartChargeSeqStatEnum;
import com.ybyc.skids.charge.param.*;
import com.ybyc.skids.charge.result.ChargeActionResult;
import com.ybyc.skids.charge.result.EquipAuthResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/charge/exchange/")
public class ChargeController {

    @Resource
    Operator operator;

    @Autowired
    OrderService orderService;

    /**
     * 查询接入token
     *
     * @return
     */
    @PostMapping("query_token")
    public Object queryAccessToken(@RequestBody RequestData<QueryTokenParam> requestData) throws Exception {
        QueryTokenParam param = requestData.getData();

        if(!operator.checkPlatform(param.getOperatorId(),param.getOperatorSecret())){
            throw new IllegalStateException("运营商秘钥不正确");
        }
        AccessToken token = AccessToken.build(param.getOperatorId());
        AccessTokenContext.put(token);
        return Response.ok(token);
    }

    /**
     * 检车设备状态
     *
     * @return
     */
    @PostMapping("query_equip_auth")
    public Object queryEquipAuth(@RequestBody RequestData<QueryEquipAuthParam> requestData) throws Exception {
        return Response.ok(EquipAuthResult.of(requestData.getData()));
    }

    @PostMapping("query_start_charge")
    public Object queryStartCharge(@RequestBody RequestData<ChargeStartParam> requestData) throws Exception {

        String orderSn = requestData.getData().getStartChargeSeq();
        String connectorId = requestData.getData().getConnectorID();

        orderService.start(orderSn,connectorId);

        ChargeActionResult actionResult = new ChargeActionResult();
        actionResult.setStartChargeSeq(orderSn);
        actionResult.setStartChargeSeqStat(StartChargeSeqStatEnum.CHARGING.getCode());
        actionResult.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        actionResult.setConnectorID(connectorId);
        return Response.ok(actionResult);
    }

    @PostMapping("query_equip_charge_status")
    public Object queryChargeStatus(@RequestBody RequestData<QueryChargeStatusParam> requestData) throws Exception {
        Order order = OrderContext.getPOOL().get(requestData.getData().getStartChargeSeq());
        return Response.ok(EquipChargeStatus.of(order));
    }

    @PostMapping("query_stop_charge")
    public Object queryStopCharge(@RequestBody RequestData<ChargeStartParam> requestData) throws Exception {
        String orderSn = requestData.getData().getStartChargeSeq();
        String connectorId = requestData.getData().getConnectorID();

        orderService.stop(orderSn,connectorId);

        ChargeActionResult actionResult = new ChargeActionResult();
        actionResult.setStartChargeSeq(orderSn);
        actionResult.setStartChargeSeqStat(StartChargeSeqStatEnum.END.getCode());
        actionResult.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        actionResult.setConnectorID(connectorId);
        return Response.ok(actionResult);
    }

}
