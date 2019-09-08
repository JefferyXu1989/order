package com.cosmose.order.service;

import com.cosmose.order.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface CustomerService {

    ResultCode save(CustomerInfo customer);

    ResultCode cancelHotelRoom(long reserveId);

    ResultResponse checkReservation(long customerId);

    ResultCode reserveRoom(ReservationInfo reservationInfo);

    Page<RoomInfo> findAvailableHotelRoom(QueryCondition queryCondition);
}
