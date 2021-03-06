package com.cosmose.order.controller;

import com.cosmose.order.entity.*;
import com.cosmose.order.enums.ResultEnum;
import com.cosmose.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xujian
 * @create 2019-09-07
 */

@RestController
@RequestMapping("/operate")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Register a order account
     * @param customer
     * @return
     */
    @PostMapping("/saveCustomerInfo")
    public ResultCode saveCustomerInfo(CustomerInfo customer){
        return customerService.save(customer);
    }

    /**
     * User can cancel his reservation
     * @param reserveId
     * @return
     */
    @PostMapping("/cancelHotelRoom")
    public ResultCode cancelHotelRoom(@RequestParam Long reserveId){
        return customerService.cancelHotelRoom(reserveId);
    }

    /**
     * User can check their reservations.
     * @param customerId
     * @return
     */
    @PostMapping("/checkReservation")
    public ResultResponse checkReservation(@RequestParam Long customerId){
        return customerService.checkReservation(customerId);
    }

    /**
     * User asks for room reservation for specific period.
     * @param reservationInfo
     * @return
     */
    @PostMapping("/reserveRoom")
    public ResultCode reserveRoom(ReservationInfo reservationInfo){
        return customerService.reserveRoom(reservationInfo);
    }

    /**
     * User searches for available hotel rooms.Search criteria include:
     * a.period
     * b.city
     * c.daily price range
     * @param queryCondition
     * @return
     */
    @PostMapping("/findAvailableHotelRoom")
    public ResultResponse findAvailableHotelRoom(QueryCondition queryCondition){
        List<RoomInfo> roomInfoList = customerService.findAvailableHotelRoom(queryCondition);
        return new ResultResponse(ResultEnum.OK, roomInfoList);
    }
}
