package org.xr.happy.order;


import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.xr.happy.Application;
import org.xr.happy.service.OrderGenerator;

import java.util.concurrent.CountDownLatch;

import static org.xr.happy.common.constant.RedisKey.ORDER_ID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderIdGeneratorTest {

    @Autowired
    private OrderGenerator orderGenerator;
    @Autowired
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Test
    public void orderGenerator(){

        redisTemplate.delete(ORDER_ID);


        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {

            new Thread(()->{
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                orderGenerator.generatorOrderId();


            }).start();
        }

        System.in.read();

    }

    public static void main(String[] args) {
        String str=null;
        str=String.format("Hi,%s", "王力");
        System.out.println(str);
        str=String.format("Hi,%s:%s.%s", "王南","王力","王张");
        System.out.println(str);
        System.out.printf("字母a的大写是：%c %n", 'A');
        System.out.printf("3>7的结果是：%b %n", 3>7);
        System.out.printf("100的一半是：%d %n", 100/2);
        System.out.printf("100的16进制数是：%x %n", 100);
        System.out.printf("100的8进制数是：%o %n", 100);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);
        System.out.printf("上面价格的16进制数是：%a %n", 50*0.85);
        System.out.printf("上面价格的指数表示：%e %n", 50*0.85);
        System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50*0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        System.out.printf("字母A的散列码是：%h %n", 'A');
    }
}
