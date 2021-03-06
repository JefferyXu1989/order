package com.cosmose.order.service;

import com.cosmose.order.entity.*;

import java.util.List;

/**
 * @author xujian
 * @create 2019-09-07
 */
public interface CustomerService {

    ResultCode save(CustomerInfo customer);

    ResultCode cancelHotelRoom(Long reserveId);

    ResultResponse checkReservation(Long customerId);

    ResultCode reserveRoom(ReservationInfo reservationInfo);

    List<RoomInfo> findAvailableHotelRoom(QueryCondition queryCondition);
}
