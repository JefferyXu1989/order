package com.cosmose.order.entity;

public enum ResultEnum {
    OK("00", "success"),
    FAIL("01", "failed"),
    CHECK_EXCEPTION("02", "miss necessary parameter"),
    EXIST("04", "records already exist");

    private String code;
    private String msg;

    ResultEnum(String code, String msg){
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
