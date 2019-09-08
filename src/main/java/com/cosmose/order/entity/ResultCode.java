package com.cosmose.order.entity;

/**
 * @author xujian
 * @create 2019-09-07
 */
public class ResultCode {
    private String code;
    private String msg;

    public ResultCode(){

    }

    public ResultCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

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
}
