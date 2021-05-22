package org.xr.happy.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.Boy;
import org.xr.boy.starter.BoyProperties;
import org.xr.happy.common.annotation.Permission;
import org.xr.happy.common.annotation.RedisCache;
import org.xr.happy.common.annotation.Validator;
import org.xr.happy.common.constant.RedisKey;
import org.xr.happy.common.constant.Token;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.exception.ServerException;
import org.xr.happy.common.utils.JwtUtil;
import org.xr.happy.common.vo.UserVo;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

@RestController
public class JwtLoginController {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoginController.class);

    private static Map<String, User> cachMap = new HashMap<>();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Boy boy;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final int CURRENT_COUNTS = 200;
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static ExecutorService executorService = Executors.newFixedThreadPool(CURRENT_COUNTS);


    @Permission(role = "admin")
    @GetMapping("/jwtLogin")
    public Result jwtLogin(@Validator UserVo userVo) {

        logger.info("boy is :{}", boy.toString());

        User users = null;
        User user = new User();
        user.setUsername("steven");
        if (cachMap.containsKey(user.getUsername())) {
            logger.debug("Obtain the user information from cach memory");
            users = cachMap.get(user.getUsername());
        } else {
            users = userMapper.selectOne(user);
            cachMap.put(user.getUsername(), users);
        }


        Long id = System.currentTimeMillis();

        String userVoJson = JSON.toJSONString(users);

        try {
            String jwtToken = JwtUtil.createJWT(id.toString(), userVoJson, 5L);

            BeanUtils.copyProperties(users, userVo);
            userVo.setUserUniqueToken(jwtToken);
            logger.info("jwt token created success !：{}", jwtToken);

            return Result.success(userVo);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException("系统异常");
        }

    }

    /**
     * Mq test
     */
    @GetMapping("sender")
    public void jwtLogin() {
        rabbitTemplate.convertAndSend("xr-blog-love", "1");
        logger.info("消息发送成功");

  /*      for (int i = 0; i < 50000; i++) {
            executorService.submit(() -> {
                rabbitTemplate.convertAndSend("xr-blog-love", "this is a test for rabbitmq!!!!" + atomicInteger.getAndIncrement());

            });

        }*/
    }


    @GetMapping("/path")
    public void getPath(HttpServletRequest request) throws Exception {

        //第1种：获取服务器二进制命令路径
        String path1 = new File("").getCanonicalPath();
        logger.info("path1 {}" + path1);

        //第2种：获取服务器二进制命令路径
        String path2 = System.getProperty("user.dir");
        logger.info("path2 {}" + path2);

        //第3种：获取项目的根目录
        String path3 = request.getRealPath("");
        logger.info("path3 {}" + path3);

        //第4种：获取项目的根目录,但Servlet2.1以后已过期
        String path4 = request.getRealPath("");
        logger.info("path4 {}" + path4);

        //第5种：获取类加载的根路径
        String path5 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        logger.info("path5 {}" + path5);

        //第6种：获取类加载的根路径
        String path6 = this.getClass().getClassLoader().getResource("").getPath();
        logger.info("path6 {}" + path6);

        //第7种：获取类加载的根路径
        String path7 = this.getClass().getResource("/").getPath();
        logger.info("path7 {}" + path7);

        //第8种：获取当前类的路径
        String path8 = this.getClass().getResource("").getPath();
        logger.info("path8 {}" + path8);

    }


    @GetMapping("/cache")
    @RedisCache(value = "uname", key = "#userId")
    public Result redisCache(String userId) {

        User user = new User();
        user.setId(1);
        user.setUsername("steven");
        user.setPassword("123456");
        user.setEmail("17635841699@163.com");
        user.setCreateTime(new Date());
        user.setCreateBy("steven");
        user.setUpdateTime(new Date());
        user.setUpdateBy("sxr");
        logger.info("Get user info from db.......");

        return Result.success(user);
    }

    @GetMapping("/redis")
    public Result redis() {

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
        Map map = null;
        try {
            map = objectMapper.readValue(objectMapper.writeValueAsString(user), Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        redisTemplate.opsForValue().set("order_timeout", "如果30秒内未支付，订单将会超时，需要重新下单哦！！！", 10, TimeUnit.SECONDS);

        // Hash值 的操作使用方式
        redisTemplate.opsForHash().putAll(Token.USER_TOKEN + "-" + user.getId().toString(), map);
        redisTemplate.expire(Token.USER_TOKEN + "-" + user.getId().toString(), 10, TimeUnit.SECONDS);

        Object o = redisTemplate.opsForHash().get(Token.USER_TOKEN + "-" + user.getId().toString(), "username");


        // stream 存储方式
        RecordId mystream = redisTemplate.opsForStream().add("mystream", map);
        String value = mystream.getValue();
        System.out.println(value);


        List mystream1 = redisTemplate.opsForStream().range("mystream", Range.unbounded());
        System.out.println(mystream1);

        Object execute = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {

                String sendMessage = "转账成功，请注意查收 。。。。";

                redisConnection.publish(RedisKey.TEST_CHANNEL_NAME.getBytes(), sendMessage.getBytes(Charset.forName("utf-8")));

                return "success";
            }
        });

        return Result.success(o);
    }

    public static void main(String[] args) {
     /*   LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("hello");

        LockSupport.unpark(Thread.currentThread());


        LockSupport.park();
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("hello");*/


        String ls = "'hello:'+#userId";
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

        Expression expression = spelExpressionParser.parseExpression(ls);


        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();

        standardEvaluationContext.setVariable("userId", "steven");


        String string = expression.getValue(standardEvaluationContext).toString();
        System.out.println(string);

    }
}
