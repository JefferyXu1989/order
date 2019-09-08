package com.cosmose.order.entity;

public class ResultResponse {
    private String code;
    private String msg;
    private Class<?> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Class<?> getData() {
        return data;
    }

    public void setData(Class<?> data) {
        this.data = data;
    }
}
