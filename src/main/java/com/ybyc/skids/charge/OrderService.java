package com.ybyc.skids.charge;

import com.ybyc.skids.charge.common.OrderContext;
import com.ybyc.skids.charge.common.model.ConnectorStatusEnum;
import com.ybyc.skids.charge.common.model.Order;
import com.ybyc.skids.charge.common.model.StartChargeSeqStatEnum;
import com.ybyc.skids.charge.result.ChargeActionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    @Autowired
    ChargeService chargeService;

    @Async
    public void start(String sn,String connectorId){
        Order order = new Order();
        order.setSn(sn);
        order.setConnectorId(connectorId);
        order.setConnectorStatusEnum(ConnectorStatusEnum.USED_CHARGING);
        order.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setStatEnum(StartChargeSeqStatEnum.CHARGING);
        OrderContext.put(order);

        //假设与桩通讯了5秒钟
        sleep(5);

        if(Math.random() < 0.2){
            order.setCurrent(0.05);
        }

        ChargeActionResult actionResult = new ChargeActionResult();
        actionResult.setStartChargeSeq(sn);
        actionResult.setStartChargeSeqStat(order.getStatEnum().getCode());
        actionResult.setStartTime(order.getStartTime());
        actionResult.setConnectorID(connectorId);
        chargeService.chargeStartNotify(actionResult);

    }

    @Async
    public void stop(String sn,String connectorId){
        Order order = OrderContext.getPOOL().get(sn);
        order.setEndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setStatEnum(StartChargeSeqStatEnum.END);
        order.setConnectorStatusEnum(ConnectorStatusEnum.USED_UNCHARGE);

        //假设与桩通讯了5秒钟
        sleep(5);

        ChargeActionResult actionResult = new ChargeActionResult();
        actionResult.setStartChargeSeq(sn);
        actionResult.setStartChargeSeqStat(order.getStatEnum().getCode());
        actionResult.setStartTime(order.getStartTime());
        actionResult.setConnectorID(connectorId);
        chargeService.chargeStopNotify(actionResult);

        chargeService.chargeInfoNotify(order);
        chargeService.chargeRecordNotify(order);

        OrderContext.getPOOL().remove(order.getSn());

    }

    @Scheduled(fixedRate = 30 * 1000)
    public void scheduleNotify(){
        //每60秒，通知订单信息
        Iterator<Map.Entry<String,Order>> it = OrderContext.getPOOL().entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Order> entry = it.next();
            Order order = entry.getValue();
            order.pulse();
            chargeService.chargeStatusNotify(order);
        }
    }


    private void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
