package org.xr.happy.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.xr.happy.Application;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringRunner.class)
public class ThreadTransactionTest {


    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void testMultiThreadTransaction() {





    }

}
