package org.xr.happy.order;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xr.happy.Application;
import org.xr.happy.service.OrderGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderIdGeneratorTest {


    @Autowired
    private OrderGenerator orderGenerator;


    @Test
    public void orderGenerator(){

        orderGenerator.generatorOrderId();
    }

}
