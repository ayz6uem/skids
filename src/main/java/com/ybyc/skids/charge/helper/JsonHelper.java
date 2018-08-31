package com.ybyc.skids.charge.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T toObject(String data, Class<T> resultClass){
        try {
            return objectMapper.readValue(data,resultClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
