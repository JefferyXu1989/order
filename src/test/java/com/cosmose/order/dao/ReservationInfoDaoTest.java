package com.cosmose.order.dao;

import com.cosmose.order.entity.ReservationInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试接口
 *
 * @author OUKELE
 * @create 2019-04-13 16:43
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
