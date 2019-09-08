package com.cosmose.order.service;

import com.cosmose.order.entity.*;

public interface CustomerService {

    ResultCode save(CustomerInfo customer);

    ResultCode cancelHotelRoom(long reserveId);

    ResultResponse checkReservation(long customerId);

    ResultCode reserveRoom(ReservationInfo reservationInfo);

}
