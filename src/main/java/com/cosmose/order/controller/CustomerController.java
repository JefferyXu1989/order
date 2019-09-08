package com.cosmose.order.controller;

import com.cosmose.order.entity.CustomerInfo;
import com.cosmose.order.entity.QueryCondition;
import com.cosmose.order.entity.ResultCode;
import com.cosmose.order.entity.ResultResponse;
import com.cosmose.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author OUKELE
 * @create 2019-04-14 17:24
 */

@RestController
@RequestMapping("/operate")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomerInfo")
    public ResultCode saveCustomerInfo(CustomerInfo customer){
        return customerService.save(customer);
    }

    @PostMapping("/cancelHotelRoom")
    public ResultCode findAvailableHotelRoom(@RequestParam long reserveId){
        return customerService.cancelHotelRoom(reserveId);
    }

    @PostMapping("/checkReservation")
    public ResultResponse checkReservation(@RequestParam long customerId){
        return customerService.checkReservation(customerId);
    }
}
