package com.cosmose.order.dao;

import com.cosmose.order.entity.ReservationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * test interface
 * @author xujian
 * @create 2019-09-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationInfoDaoTest {

    @Autowired
    private ReservationInfoDao reservationInfoDao;

    @Test
    public void findReservationInfoByStatus(){
        List<ReservationInfo> list = reservationInfoDao.findReservationInfoByStatus();
        System.out.println(list.size());
    }
}
