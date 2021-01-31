package org.xr.happy.controller;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.annotation.Permission;
import org.xr.happy.common.annotation.Validator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

@RestController
public class JwtLoginController {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoginController.class);

    private static Map<String, User> cachMap = new HashMap<>();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Permission(role = "admin")
    @GetMapping("jwtLogin")
    public Result jwtLogin(@Validator UserVo userVo) {

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

        rabbitTemplate.convertAndSend("xr-blog-love", "this is a test for rabbitmq!!!!");
        logger.info("消息发送成功");

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

    public static void main(String[] args) {
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("hello");

        LockSupport.unpark(Thread.currentThread());


        LockSupport.park();
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("hello");

    }
}
