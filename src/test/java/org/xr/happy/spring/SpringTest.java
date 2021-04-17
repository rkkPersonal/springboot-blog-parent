package org.xr.happy.spring;

import java.util.Arrays;
import java.util.Date;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xr.happy.Application;
import org.xr.happy.model.User;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class SpringTest {


    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testRedisHash() throws Exception {


    }

    private void testRedisSubscriptPublish() throws Exception {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                connection.subscribe((message, pattern) -> {
                    System.out.println("收到:" + message.toString() + "," + Arrays.toString(pattern));

                }, "__keyspace@0__:steven66".getBytes());
                return null;
            }
        });


        redisTemplate.opsForValue().set("steven66", "this is a test keyevent");

        Thread.sleep(1000);
        System.out.println("---------------开始删除key----------------");
        //redisTemplate.delete("steven66");
        redisTemplate.expire("steven66", 1, TimeUnit.SECONDS);


        Thread.sleep(3000);

        //收到:set,null
        //---------------开始删除key----------------
        //收到:del,null
//---------------------------------------------------------------
        // 收到:set,null
        //---------------开始删除key----------------
        //收到:expire,null key event 类型
        //收到:expired,null key event 描述 代表已经过期了
    }

    private void testHash() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("steven");
        user.setPassword("123456");
        user.setEmail("17635841699@163.com");
        user.setCreateTime(new Date());
        user.setCreateBy("steven");
        user.setUpdateTime(new Date());
        user.setUpdateBy("sxr");

        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(user.toString(), Map.class);
        redisTemplate.opsForHash().putAll(user.getId(), map);


        Object o = redisTemplate.opsForHash().get(user.getId(), user.getUsername());

        System.out.println(o);

    }
}
