package com.cosmose.order.enums;

/**
 * @author xujian
 * @create 2019-09-07
 */
public enum ResultEnum {
    OK("00", "success"),
    FAIL("01", "failed"),
    CHECK_EXCEPTION("02", "miss necessary parameter"),
    EXIST("03", "records already exist"),
    NOTEXIST("04", "records does not exist"),
    NO_AVAILABLE("05", "no available room exist"),
    ALREADY_RESERVED("06", "room has been already reserved");

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
