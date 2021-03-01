package org.xr.happy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.xr.happy.common.constant.RedisKey;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.dto.Room;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {

        List<User> users = userMapper.selectAll();

        users.forEach(user -> {
            Integer id = user.getId();

            int i = id.hashCode();

            long index = (long) (i / Math.pow(2, 32));
            redisTemplate.opsForValue().setBit(RedisKey.USER_BLOOM_FILTER, index, true);
        });


    }

    @PostMapping(value = "/checkSumRoom")
    public Result checkRoomSum(@RequestBody Room room) {
        int i = new Random().nextInt(1000);
        Room build = Room.builder().sum(i).build();
        return Result.success(build);
    }


    @PostMapping(value = "/queryOrder")
    public Result queryOrder(@RequestParam("userId") Long userId) {
        int i = userId.hashCode();

        long index = (long) (i / Math.pow(2, 32));

        Boolean result = redisTemplate.opsForValue().getBit(RedisKey.USER_BLOOM_FILTER, index);
        if (!result) {
            logger.info("用户不存在:" + userId);

            return Result.error("用户不存在");
        }

        return Result.ok();
    }
}
