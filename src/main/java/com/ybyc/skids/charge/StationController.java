package com.ybyc.skids.charge;

import com.ybyc.common.model.ResultData;
import com.ybyc.skids.charge.common.model.ConnectorStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge/station")
public class StationController {

    @Autowired
    ChargeService chargeService;

    @RequestMapping
    public ResultData status(String connectorId, ConnectorStatusEnum statusEnum){
        chargeService.stationStatusNotify(connectorId,statusEnum);
        return ResultData.ok();
    }

}
