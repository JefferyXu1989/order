package com.cosmose.order.service.Impl;

import com.cosmose.order.Utils.MD5Utils;
import com.cosmose.order.Utils.NativeResultProcessUtils;
import com.cosmose.order.dao.CustomerDao;
import com.cosmose.order.dao.ReservationInfoDao;
import com.cosmose.order.dao.RoomInfoDao;
import com.cosmose.order.dto.ReservationDto;
import com.cosmose.order.entity.*;
import com.cosmose.order.enums.ReserveStatusEnum;
import com.cosmose.order.enums.ResultEnum;
import com.cosmose.order.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xujian
 * @create 2019-09-07
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
        if(checkParams(customer)){
            return new ResultCode(ResultEnum.CHECK_EXCEPTION);
        }
        CustomerInfo customerInfo = customerDao.findCustomerInfoByMobileNo(customer.getMobileNo());
        if(customerInfo != null){
            return new ResultCode(ResultEnum.EXIST);
        }
        customer.setEncPwd(MD5Utils.stringToMD5(customer.getEncPwd()));
        customer.setCreatedAt(new Date());
        customer.setCreatedBy("SYS");
        customer.setUpdatedAt(new Date());
        customer.setUpdatedBy("SYS");
        int result = customerDao.save(customer)!= null ? 1:0;
        if (result>0){
            return new ResultCode(ResultEnum.OK);
        }else {
            return new ResultCode(ResultEnum.FAIL);
        }
    }

    public ResultCode cancelHotelRoom(Long reserveId){
        if(reserveId == null){
            return new ResultCode(ResultEnum.CHECK_EXCEPTION);
        }
        ReservationInfo reservationInfo = reservationInfoDao.findReservationInfoByReserveId(reserveId);
        if(reservationInfo == null){
            return new ResultCode(ResultEnum.NOTEXIST);
        }
        int result = reservationInfoDao.updateStatusToCancel(reserveId);
        if (result == 1){
            return new ResultCode(ResultEnum.OK);
        }else {
            return new ResultCode(ResultEnum.FAIL);
        }
    }

    public ResultResponse checkReservation(Long customerId){
        if(customerId == null){
            return new ResultResponse(ResultEnum.CHECK_EXCEPTION);
        }
        List<Tuple> tupleList = roomInfoDao.findReservationByCustomerId(customerId);
        List<ReservationDto> reservationDtoList = new ArrayList<ReservationDto>();
        for(int i = 0; i < tupleList.size(); i ++){
            ReservationDto reservationDto = new ReservationDto();
            reservationDto = NativeResultProcessUtils.processResult(tupleList.get(i), ReservationDto.class);
            reservationDtoList.add(reservationDto);
        }
        if(CollectionUtils.isEmpty(reservationDtoList)){
            return new ResultResponse(ResultEnum.NOTEXIST);
        }
        return new ResultResponse(ResultEnum.OK, reservationDtoList);
    }

    public ResultCode reserveRoom(ReservationInfo reservationInfo){
        if(reservationInfo.getRoomId() == null || reservationInfo.getCustomerId() == null||
            StringUtils.isBlank(reservationInfo.getStartDate()) || StringUtils.isBlank(reservationInfo.getLastDate())){
            return new ResultCode(ResultEnum.CHECK_EXCEPTION);
        }
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setStartDate(reservationInfo.getStartDate());
        queryCondition.setEndDate(reservationInfo.getLastDate());
        List<RoomInfo> roomInfoList = findAvailableHotelRoom(queryCondition);
        if(CollectionUtils.isEmpty(roomInfoList)){
            return new ResultCode(ResultEnum.NO_AVAILABLE);
        }
        int count = 0;
        for(int i = 0; i < roomInfoList.size(); i ++){
            if(roomInfoList.get(i).getRoomId() != reservationInfo.getRoomId()){
                count ++;
            }
        }
        if(count == roomInfoList.size()){
            return new ResultCode(ResultEnum.ALREADY_RESERVED);
        }
        reservationInfo.setStatus(ReserveStatusEnum.SUCCESS.getCode());
        reservationInfo.setCreatedAt(new Date());
        reservationInfo.setCreatedBy("SYS");
        reservationInfo.setUpdatedAt(new Date());
        reservationInfo.setUpdatedBy("SYS");
        int result = reservationInfoDao.save(reservationInfo)!= null ? 1:0;
        if (result>0){
            return new ResultCode(ResultEnum.OK);
        }else {
            return new ResultCode(ResultEnum.FAIL);
        }
    }

    public List<RoomInfo> findAvailableHotelRoom(QueryCondition queryCondition) {
        List<RoomInfo> roomInfoList = roomInfoDao.findRoomInfoByQueryCondition(queryCondition.getCityName(), queryCondition.getStartDailyPrice(), queryCondition.getEndDailyPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date queryStartDate = null;
        Date queryEndDate = null;
        Date checkinDate = null;
        Date checkoutDate = null;
        List<ReservationInfo> reservationInfoList = reservationInfoDao.findReservationInfoByStatus();
        if (CollectionUtils.isEmpty(roomInfoList) || CollectionUtils.isEmpty(reservationInfoList)) {
            return roomInfoList;
        }
        for(int i = 0; i < reservationInfoList.size(); i ++){
            try {
                queryStartDate = sdf.parse(queryCondition.getStartDate());
                queryEndDate = sdf.parse(queryCondition.getEndDate());
                checkinDate = sdf.parse(reservationInfoList.get(i).getStartDate());;
                checkoutDate = sdf.parse(reservationInfoList.get(i).getLastDate());;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            if((checkinDate.compareTo(queryStartDate) >= 0
               && checkinDate.compareTo(queryEndDate) <= 0) ||
                (checkoutDate.compareTo(queryStartDate) >= 0
                 && checkoutDate.compareTo(queryEndDate) <= 0) ||
                (checkinDate.compareTo(queryStartDate) == -1
                && checkoutDate.compareTo(queryEndDate) == 1)){
                Iterator<RoomInfo> iterator = roomInfoList.iterator();
                while (iterator.hasNext()) {
                    if(reservationInfoList.get(i).getRoomId() == iterator.next().getRoomId()){
                        iterator.remove();
                    }
                }
            }
        }
        return roomInfoList;
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
