package com.cosmose.order.entity;

import com.cosmose.order.enums.ResultEnum;

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

    public ResultCode(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
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
