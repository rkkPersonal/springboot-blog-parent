package org.xr.happy.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xr.happy.common.constant.Token;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.utils.RedisOperator;
import org.xr.happy.common.vo.UserVo;

import java.util.UUID;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisOperator redisOperator;

    @GetMapping("/signIn")
    public Result signIn(UserVo users) {


        // 伪代码
        UserVo userVo = this.getUserInfo();

        String userUniqueToken = UUID.randomUUID().toString();
        userVo.setUserUniqueToken(userUniqueToken);
        redisOperator.set(Token.USER_TOKEN + ":" + userVo.getUserId(), userUniqueToken);

        // 将用户信息写道 cookie
        logger.info("user sign in is successfully!!");
        return Result.success(userVo);

    }

    private UserVo getUserInfo() {
        UserVo userVo = new UserVo();
        userVo.setUserId(10L);

        return userVo;
    }
}
