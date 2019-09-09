package com.cosmose.order.entity;

import com.cosmose.order.enums.ResultEnum;

/**
 * @author xujian
 * @create 2019-09-07
 */
public class ResultResponse<T> {
    private String code;
    private String msg;
    private T data;

    public  ResultResponse(){
    }

    public ResultResponse(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public ResultResponse(ResultEnum resultEnum, T data){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
