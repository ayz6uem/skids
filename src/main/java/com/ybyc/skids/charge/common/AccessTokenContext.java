package com.ybyc.skids.charge.common;

import com.ybyc.skids.charge.common.model.AccessToken;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenContext {

    public static Map<String,AccessToken> pool = new HashMap<>();

    public static void put(AccessToken accessToken){
        pool.put(accessToken.getOperatorID(),accessToken);
    }

    public static AccessToken get(String operatorId){
        return pool.get(operatorId);
    }

    public static boolean contain(String operatorId){
        return pool.containsKey(operatorId);
    }

}
