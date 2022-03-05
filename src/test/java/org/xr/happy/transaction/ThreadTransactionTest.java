package org.xr.happy.transaction;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.xr.happy.Application;
import org.xr.happy.service.order.OrderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringRunner.class)
public class ThreadTransactionTest {

    @Resource
    private OrderService orderService;



    @Test
    public void testTransaction(){

        orderService.methodA();

    }


}
