package org.xr.happy.spring;
import java.util.Date;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.xr.happy.Application;
import org.xr.happy.model.User;

import java.util.Map;

@SpringBootTest(classes = Application.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class SpringTest {


    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testRedisHash() throws Exception{

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
        redisTemplate.opsForHash().putAll(user.getId(),map);


        Object o = redisTemplate.opsForHash().get(user.getId(), user.getUsername());

        System.out.println(o);


    }

}
