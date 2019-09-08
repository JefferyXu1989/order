package com.cosmose.order.service.Impl;

import com.cosmose.order.Utils.NativeResultProcessUtils;
import com.cosmose.order.dao.CustomerDao;
import com.cosmose.order.dao.ReservationInfoDao;
import com.cosmose.order.dao.RoomInfoDao;
import com.cosmose.order.dto.ReservationDto;
import com.cosmose.order.entity.*;
import com.cosmose.order.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public ResultResponse findAvailableHotelRoom(QueryCondition queryCondition) {
        ResultResponse resultResponse = new ResultResponse();
        Pageable pageable =  PageRequest.of(queryCondition.getPageNum(), queryCondition.getPageSize(), Sort.Direction.ASC, "roomId");
        Specification<RoomInfo> sp = new Specification<RoomInfo>() {
            @Override
            public Predicate toPredicate(Root<RoomInfo> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryCondition != null) {
                    if (StringUtils.isNotBlank(queryCondition.getCityName())) {
                        predicate.getExpressions().add(cb.equal(r.<String> get("city"), StringUtils.trim(queryCondition.getCityName())));
                    }
                    if (queryCondition.getStartDailyPrice() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(r.<BigDecimal> get("dailyPrice"), queryCondition.getStartDailyPrice()));
                    }
                    if (queryCondition.getEndDailyPrice() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(r.<BigDecimal> get("dailyPrice"), queryCondition.getEndDailyPrice()));
                    }
                }
                return predicate;
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date queryStartDate = null;
        Date queryEndDate = null;
        Date checkinDate = null;
        Date checkoutDate = null;
        List<ReservationInfo> reservationInfoList = reservationInfoDao.findReservationInfoByStatus();
        Page<RoomInfo> roomInfoPage = roomInfoDao.findAll(sp, pageable);
        if (CollectionUtils.isEmpty(roomInfoPage.getContent()) || CollectionUtils.isEmpty(reservationInfoList)) {
            resultResponse.setCode(ResultEnum.OK.getCode());
            resultResponse.setMsg(ResultEnum.OK.getMsg());
            resultResponse.setData(roomInfoPage.getContent());
            return resultResponse;
        }
        List<RoomInfo> list = new ArrayList<RoomInfo>(roomInfoPage.getContent());
        for(int i = 0; i < reservationInfoList.size(); i ++){
            try {
                queryStartDate = sdf.parse(queryCondition.getStartDate());
                queryEndDate = sdf.parse(queryCondition.getEndDate());
                checkinDate = sdf.parse(reservationInfoList.get(i).getStartDate());;
                checkoutDate = sdf.parse(reservationInfoList.get(i).getLastDate());;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if((checkinDate.compareTo(queryStartDate) >= 0
               && checkinDate.compareTo(queryEndDate) <= 0) ||
                (checkoutDate.compareTo(queryStartDate) >= 0
                 && checkoutDate.compareTo(queryEndDate) <= 0) ||
                (checkinDate.compareTo(queryStartDate) == -1
                && checkoutDate.compareTo(queryEndDate) == 1)){
                Iterator<RoomInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    if(reservationInfoList.get(i).getRoomId() == iterator.next().getRoomId()){
                        iterator.remove();
                    }
                }
            }
        }
        resultResponse.setCode(ResultEnum.OK.getCode());
        resultResponse.setMsg(ResultEnum.OK.getMsg());
        resultResponse.setData(list);
        return resultResponse;
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
