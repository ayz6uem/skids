package com.ybyc.simulator.car.tbox.threadcn;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ThreadcnContext {

    @Value("${tbox.threadcn.host}")
    private String host;
    @Value("${tbox.threadcn.port}")
    private int port;
    @Value("${tbox.threadcn.idle}")
    private int idle;

}
