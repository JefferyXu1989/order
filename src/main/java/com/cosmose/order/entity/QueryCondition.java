package com.cosmose.order.entity;

import java.math.BigDecimal;
import java.util.Date;

public class QueryCondition {
    private String cityName;
    private BigDecimal startDailyPrice;
    private BigDecimal endDailyPrice;
    private Date startDate;
    private Date endDate;
    private Integer pageNum;
    private Integer pageSize;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public BigDecimal getStartDailyPrice() {
        return startDailyPrice;
    }

    public void setStartDailyPrice(BigDecimal startDailyPrice) {
        this.startDailyPrice = startDailyPrice;
    }

    public BigDecimal getEndDailyPrice() {
        return endDailyPrice;
    }

    public void setEndDailyPrice(BigDecimal endDailyPrice) {
        this.endDailyPrice = endDailyPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
