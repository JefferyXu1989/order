package com.cosmose.order.dao;

import com.cosmose.order.entity.ReservationInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface ReservationInfoDao extends PagingAndSortingRepository<ReservationInfo, Long>, JpaSpecificationExecutor<ReservationInfo> {
    @Query("select t from ReservationInfo t where t.reserveId = :reserveId and t.status = 1")
    public ReservationInfo findReservationInfoByReserveId(@Param("reserveId") long reserveId);

    @Modifying
    @Transactional
    @Query("update ReservationInfo t set t.status = 2, t.updatedAt = now() where t.reserveId = :reserveId and t.status = 1")
    public int updateStatusToCancel(@Param("reserveId") long reserveId);

    @Query("select t from ReservationInfo t where t.status = 1")
    public List<ReservationInfo> findReservationInfoByStatus();
}
