package com.ybyc.skids.tbox.hikvision;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class HikvisionContext {

    @Value("${tbox.hikvision.host}")
    private String host;
    @Value("${tbox.hikvision.port}")
    private int port;
    @Value("${tbox.hikvision.idle}")
    private int idle;

}
