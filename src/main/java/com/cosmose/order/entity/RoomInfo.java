package com.cosmose.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "room_info", schema = "orderbusiness")
public class RoomInfo {
    private long roomId;
    private String roomNum;
    private String hotelName;
    private BigDecimal dailyPrice;
    private String city;
    private String remark;
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;

    @Id
    @Column(name = "room_id")
    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "room_num")
    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    @Basic
    @Column(name = "hotel_name")
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Basic
    @Column(name = "daily_price")
    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomInfo roomInfo = (RoomInfo) o;
        return roomId == roomInfo.roomId &&
                Objects.equals(roomNum, roomInfo.roomNum) &&
                Objects.equals(hotelName, roomInfo.hotelName) &&
                Objects.equals(dailyPrice, roomInfo.dailyPrice) &&
                Objects.equals(city, roomInfo.city) &&
                Objects.equals(remark, roomInfo.remark) &&
                Objects.equals(createdAt, roomInfo.createdAt) &&
                Objects.equals(createdBy, roomInfo.createdBy) &&
                Objects.equals(updatedAt, roomInfo.updatedAt) &&
                Objects.equals(updatedBy, roomInfo.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomNum, hotelName, dailyPrice, city, remark, createdAt, createdBy, updatedAt, updatedBy);
    }
}
