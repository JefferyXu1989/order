package com.cosmose.order.dao;

import com.cosmose.order.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface RoomInfoDao extends PagingAndSortingRepository<RoomInfo, Long>, JpaSpecificationExecutor<RoomInfo>, JpaRepository<RoomInfo, Long> {
    @Query("select a.reserveId as reserveId,a.startDate as startDate,a.lastDate as lastDate,a.status as status,t.roomNum as roomNum,t.hotelName as hotelName,t.dailyPrice as dailyPrice,t.city as city from RoomInfo t left join ReservationInfo a on t.roomId = a.roomId where a.status = 1 and a.customerId = :customerId")
    public List<Tuple> findReservationByCustomerId(@Param("customerId") long customerId);

}
