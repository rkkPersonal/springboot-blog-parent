package org.xr.happy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "布隆过滤器测试",value = "redis 加 内存")
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

    @ApiOperation( value = "room 参数")
    @PostMapping(value = "/checkSumRoom")
    public Result checkRoomSum(@RequestBody Room room) {
        int i = new Random().nextInt(1000);
        Room build = Room.builder().sum(i).build();
        // 模拟业务处理超时时间
        int simulator = new Random().nextInt(1000 * 5);
        try {
            Thread.sleep(simulator);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(build);
    }


    @GetMapping(value = "/queryOrder")
    @ApiOperation(value = "查询订单",notes = "主要用来测订单是否存在")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId",value = "用户ID",defaultValue = "1",required = true,dataType = "Long"),
            @ApiImplicitParam(name = "roomId",value = "房间号码",defaultValue = "99",required = true,dataType = "String")
    })
    public Result queryOrder(@RequestParam(value = "userId",defaultValue = "1") Long userId,
                             @RequestParam(value = "roomId",defaultValue = "99") String roomId) {
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
