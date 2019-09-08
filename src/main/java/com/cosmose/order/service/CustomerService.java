package com.cosmose.order.service;

import com.cosmose.order.entity.*;

import java.util.List;

public interface CustomerService {

    ResultCode save(CustomerInfo customer);

    ResultCode cancelHotelRoom(long reserveId);

}
