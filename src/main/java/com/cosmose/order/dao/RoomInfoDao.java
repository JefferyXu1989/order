package com.cosmose.order.dao;

import com.cosmose.order.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xujian
 * @create 2019-09-07
 */
public interface RoomInfoDao extends PagingAndSortingRepository<RoomInfo, Long>, JpaSpecificationExecutor<RoomInfo>, JpaRepository<RoomInfo, Long> {
    @Query("select a.reserveId as reserveId,a.startDate as startDate,a.lastDate as lastDate,a.status as status,t.roomNum as roomNum,t.hotelName as hotelName,t.dailyPrice as dailyPrice,t.city as city from RoomInfo t left join ReservationInfo a on t.roomId = a.roomId where a.status = 1 and a.customerId = :customerId")
    public List<Tuple> findReservationByCustomerId(@Param("customerId") long customerId);

    @Query(value = "select * from room_info where if(?1 !='',city=?1,1=1) and if(?2 !='',daily_price>=?2,1=1)" +
            "and if(?3 !='',daily_price<=?3,1=1)  ",nativeQuery = true)
    public List<RoomInfo> findRoomInfoByQueryCondition(String cityName, BigDecimal startDailyPrice, BigDecimal endDailyPrice);
}
