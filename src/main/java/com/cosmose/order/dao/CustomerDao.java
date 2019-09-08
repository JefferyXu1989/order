package com.cosmose.order.dao;

import com.cosmose.order.entity.CustomerInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author xujian
 * @create 2019-09-07
 */
public interface CustomerDao extends CrudRepository<CustomerInfo,Integer> {
    @Query("select t from CustomerInfo t where t.mobileNo = :mobileNo")
    public CustomerInfo findCustomerInfoByMobileNo(@Param("mobileNo") String mobileNo);

}
