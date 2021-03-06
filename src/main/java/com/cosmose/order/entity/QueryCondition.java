package com.cosmose.order.entity;

import java.math.BigDecimal;

/**
 * @author xujian
 * @create 2019-09-07
 */
public class QueryCondition {
    private String cityName;
    private BigDecimal startDailyPrice;
    private BigDecimal endDailyPrice;
    private String startDate;
    private String endDate;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
