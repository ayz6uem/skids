package com.ybyc.skids.common.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybyc.common.model.ResultData;

/**
 * http返回结果
 * @author wangzhe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> extends ResultData<T> {

    public static final String CODE_FAIL = "100";
    public static final String CODE_OFFLINE = "101";
    public static final String CODE_TIMEOUT = "102";
    public static final String CODE_BUSY = "103";
    public static final String CODE_NOT_SUPPORT = "104";
    public static final String CODE_ERROR = "500";

    public static final String CODE_SPEED = "11";
    public static final String CODE_KEY = "14";
    public static final String CODE_DOOR = "15";
    public static final String CODE_STARTUP = "17";

    public Result() {
    }

    public Result(Boolean ok, ResultData failedResult) {
        super(ok, failedResult);
    }

    public Result(String code) {
        super(code);
    }

    public Result(String code, String msg) {
        super(code, msg);
    }

    public Result(String code, String msg, T data) {
        super(code, msg, data);
    }


    public Boolean result(){
        return this.isOk();
    }


    public static Result success(){
        Result result = new Result();
        result.setCode(OK_CODE);
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T t){
        Result<T> result = new Result<>();
        result.setCode(OK_CODE);
        result.setMsg("成功");
        result.setData(t);
        return result;
    }

    public static Result error(String msg){
        return new Result(CODE_ERROR,msg);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(CODE_FAIL,msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(String.valueOf(code),msg);
    }

    public static Result speed() {
        return new Result<>(CODE_SPEED,"速度不为0");
    }

    public static Result key() {
        return new Result<>(CODE_KEY,"未感应到钥匙");
    }

    public static Result door() {
        return new Result<>(CODE_DOOR,"门未关");
    }

    public static Result startup() {
        return new Result<>(CODE_STARTUP,"未熄火");
    }

}
