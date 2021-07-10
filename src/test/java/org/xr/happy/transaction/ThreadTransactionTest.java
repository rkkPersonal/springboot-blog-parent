package org.xr.happy.transaction;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.xr.happy.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringRunner.class)
public class ThreadTransactionTest {


    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Test
    public void testTransaction(){
        List<Integer> result=new ArrayList<>();
        MultiThreadTransactionComponent mttc = new MultiThreadTransactionComponent(platformTransactionManager,threadPoolExecutor);
        for(int k=0;k<10;k++){
            int i = RandomUtils.nextInt(0, 5);
            int y= RandomUtils.nextInt(1,5);
            //添加要执行的业务代码
            mttc.addFunction(()->{
                System.out.println("当前线程：" + Thread.currentThread().getName());
                System.out.println(i%y);  //除数为0时 执行失败
                return i%y;
            });
        }
        result= mttc.execute();
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }


}
