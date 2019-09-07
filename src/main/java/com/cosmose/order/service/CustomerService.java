package com.cosmose.order.service;

import com.cosmose.order.entity.CustomerInfo;
import com.cosmose.order.entity.ResultCode;

import java.util.List;

public interface CustomerService {

    ResultCode save(CustomerInfo customer);

    List<CustomerInfo> findAll();

}
