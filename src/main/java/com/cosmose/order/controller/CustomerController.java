package com.cosmose.order.controller;

import com.cosmose.order.entity.*;
import com.cosmose.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author xujian
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

    @PostMapping("/reserveRoom")
    public ResultCode reserveRoom(ReservationInfo reservationInfo){
        return customerService.reserveRoom(reservationInfo);
    }

    @PostMapping("/findAvailableHotelRoom")
    public ResultResponse findAvailableHotelRoom(QueryCondition queryCondition){
        return customerService.findAvailableHotelRoom(queryCondition);
    }
}
