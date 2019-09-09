package com.cosmose.order.enums;

public enum ReserveStatusEnum {
    SUCCESS(1, "reserve success"),
    CANCEL(2, "cancel");

    private Integer code;
    private String desc;

    ReserveStatusEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
