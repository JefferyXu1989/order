package com.cosmose.order.serviceImpl;

import com.cosmose.order.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author OUKELE
 * @create 2019-04-14 17:11
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void getList(){
        System.out.println(customerService.checkReservation(12));
    }

}
