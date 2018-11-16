package com.winbaoxian.module.security.model.common;


import com.winbaoxian.module.security.model.enums.JsonResultCodeEnum;

/**
 * 返回前端数据格式
 */
public class JsonResult<T> {

    private Integer code;
    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static <T> JsonResult<T> createNewInstance(JsonResultCodeEnum codeEnum, String msg, T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(codeEnum.getValue());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> createSuccessResult(T data) {
        return createNewInstance(JsonResultCodeEnum.SUCCESS, null, data);
    }

    public static JsonResult createSuccessResult(String msg) {
        return createNewInstance(JsonResultCodeEnum.SUCCESS, msg, null);
    }

    public static JsonResult createErrorResult(String msg) {
        return createNewInstance(JsonResultCodeEnum.FAIL, msg, null);
    }

}
