package org.xr.happy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.UserService;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User createUser(final int c) {
        User user = new User();
        user.setUsername("steven" + c);
        user.setPassword("123456" + c);
        user.setEmail("909563510@qq.com");
        user.setCreateTime(new Date());
        user.setCreateBy("system");
        user.setUpdateTime(new Date());
        user.setUpdateBy("system");
        int insert = userMapper.insert(user);

        // 这个之所以可以搜到，是因为 在同一个事务中可以查到数据，
/*
        User users = new User();
        users.setId(user.getId());
        User user1 = userMapper.selectOne(users);
        if (user1 == null) {
            logger.info("查询失败---- userId：" + user.getId());
            return user1;
        } else {
            logger.info("查询成功-----：" + user.getId());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("插入成功 userId ：" + user.getId());
                User userByUserId = findUserByUserId(user.getId());
            }
        }).start();
*/
        if (insert>0){
            logger.info("插入成功：userId:"+user.getId());
            rabbitTemplate.convertAndSend("xr-blog-love", user.getId());

        }

        //simulated the delay commit the transaction, the  async will not be query the data ,because the transaction is not commit and
        // the async is o new thread ,different transaction ,so must commit ,then can query the data,
        //
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private User findUserByUserId(Integer userId) {
        User user = new User();
        user.setId(userId);
        User user1 = userMapper.selectOne(user);
        if (user1 == null) {
            logger.info("查询失败 userId：" + userId);
            return user1;
        } else {
            logger.info("查询成功：" + userId);
        }
        return user1;
    }
}
