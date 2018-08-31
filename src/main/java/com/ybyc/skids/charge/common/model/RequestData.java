package com.ybyc.skids.charge.common.model;

import lombok.Data;

/**
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class RequestData<T> {
    //运营商ID
    private String operatorID;
    private T data;

}
