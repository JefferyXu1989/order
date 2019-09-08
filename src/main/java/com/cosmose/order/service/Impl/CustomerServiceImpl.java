package com.cosmose.order.service.Impl;

import com.cosmose.order.Utils.NativeResultProcessUtils;
import com.cosmose.order.dao.CustomerDao;
import com.cosmose.order.dao.ReservationInfoDao;
import com.cosmose.order.dao.RoomInfoDao;
import com.cosmose.order.dto.ReservationDto;
import com.cosmose.order.entity.*;
import com.cosmose.order.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author OUKELE
 * @create 2019-04-13 18:24
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ReservationInfoDao reservationInfoDao;
    @Autowired
    private RoomInfoDao roomInfoDao;

    @Override
    public ResultCode save(CustomerInfo customer) {
        ResultCode resultCode = new ResultCode();
        if(checkParams(customer)){
            resultCode.setCode(ResultEnum.CHECK_EXCEPTION.getCode());
            resultCode.setMsg(ResultEnum.CHECK_EXCEPTION.getMsg());
            return resultCode;
        }
        CustomerInfo customerInfo = customerDao.findCustomerInfoByMobileNo(customer.getMobileNo());
        if(customerInfo != null){
            resultCode.setCode(ResultEnum.EXIST.getCode());
            resultCode.setMsg(ResultEnum.EXIST.getMsg());
            return resultCode;
        }
        customer.setCreatedAt(new Date());
        customer.setCreatedBy("SYS");
        customer.setUpdatedAt(new Date());
        customer.setUpdatedBy("SYS");
        int result = customerDao.save(customer)!= null ? 1:0;
        if (result>0){
            resultCode.setCode(ResultEnum.OK.getCode());
            resultCode.setMsg(ResultEnum.OK.getMsg());
        }else {
            resultCode.setCode(ResultEnum.FAIL.getCode());
            resultCode.setMsg(ResultEnum.FAIL.getMsg());
        }
        return resultCode;
    }

    public ResultCode cancelHotelRoom(long reserveId){
        ResultCode resultCode = new ResultCode();
        ReservationInfo reservationInfo = reservationInfoDao.findReservationInfoByReserveId(reserveId);
        if(reservationInfo == null){
            resultCode.setCode(ResultEnum.NOTEXIST.getCode());
            resultCode.setMsg(ResultEnum.NOTEXIST.getMsg());
            return resultCode;
        }
        int result = reservationInfoDao.updateStatusToCancel(reserveId);
        if (result == 1){
            resultCode.setCode(ResultEnum.OK.getCode());
            resultCode.setMsg(ResultEnum.OK.getMsg());
        }else {
            resultCode.setCode(ResultEnum.FAIL.getCode());
            resultCode.setMsg(ResultEnum.FAIL.getMsg());
        }
        return resultCode;
    }

    public ResultResponse checkReservation(long customerId){
        ResultResponse resultResponse =  new ResultResponse();
        List<Tuple> tupleList = roomInfoDao.findReservationByCustomerId(customerId);
        List<ReservationDto> reservationDtoList = new ArrayList<ReservationDto>();
        for(int i = 0; i < tupleList.size(); i ++){
            ReservationDto reservationDto = new ReservationDto();
            reservationDto = NativeResultProcessUtils.processResult(tupleList.get(i), ReservationDto.class);
            reservationDtoList.add(reservationDto);
        }
        if(CollectionUtils.isEmpty(reservationDtoList)){
            resultResponse.setCode(ResultEnum.NOTEXIST.getCode());
            resultResponse.setMsg(ResultEnum.NOTEXIST.getMsg());
        }
        resultResponse.setCode(ResultEnum.OK.getCode());
        resultResponse.setMsg(ResultEnum.OK.getMsg());
        resultResponse.setData(reservationDtoList);
        return resultResponse;
    }

    public ResultCode reserveRoom(ReservationInfo reservationInfo){
        ResultCode resultCode = new ResultCode();
        reservationInfo.setStatus(1);
        reservationInfo.setCreatedAt(new Date());
        reservationInfo.setCreatedBy("SYS");
        reservationInfo.setUpdatedAt(new Date());
        reservationInfo.setUpdatedBy("SYS");
        int result = reservationInfoDao.save(reservationInfo)!= null ? 1:0;
        if (result>0){
            resultCode.setCode(ResultEnum.OK.getCode());
            resultCode.setMsg(ResultEnum.OK.getMsg());
        }else {
            resultCode.setCode(ResultEnum.FAIL.getCode());
            resultCode.setMsg(ResultEnum.FAIL.getMsg());
        }
        return resultCode;
    }

    public Boolean checkParams(CustomerInfo customer){
        Boolean flag = false;
        if(customer == null){
            flag = true;
        }
        if(customer.getBirthday() == null || customer.getGender() == null ||
        customer.getMobileNo() == null || customer.getUserName() == null ||
        customer.getEncPwd() == null){
            flag = true;
        }
        return flag;
    }
}
