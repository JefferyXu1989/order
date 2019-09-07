package com.cosmose.order.dao;

import com.cosmose.order.entity.CustomerInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CustomerDao extends CrudRepository<CustomerInfo,Integer> {
    @Query("select t from CustomerInfo t where t.mobileNo = :mobileNo")
    public CustomerInfo findCustomerInfoByMobileNo(@Param("mobileNo") String mobileNo);
}
