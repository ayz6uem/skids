package com.ybyc.skids.charge.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Objects;

/**
 * 参数返回
 *
 * @author Aiqing
 * @date 2017/11/9
 */
@Data
public class ResponseData<T> {

    //参数编码
    private Integer ret;
    //提示信息
    private String msg;
    //参数内容
    private T data;

    @JsonIgnore
    public boolean isOk(){
        return Objects.equals(0,ret);
    }

}
