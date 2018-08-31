package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.common.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderContext {

    private static Map<String,Order> POOL = new HashMap<>();

    public static void put(Order order){
        POOL.put(order.getSn(),order);
    }

    public static Map<String,Order> getPOOL(){
        return POOL;
    }

}
