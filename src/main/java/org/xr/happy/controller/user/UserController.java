package org.xr.happy.controller.user;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xr.happy.common.annotation.Validator;
import org.xr.happy.common.dto.Result;
import org.xr.happy.model.User;
import org.xr.happy.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * user controller to manager user info
 *
 * @author Steven
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;


    @PostMapping(path = "/add")
    @ResponseBody
    public Result addUser(@Validator User user, HttpServletResponse response) {

        logger.info("userInfo :{}", JSON.toJSONString(user));
        boolean b = userService.addUser(user);
        if (!b){
            return Result.error("添加用户失败");
        }
        return Result.ok();
    }

    @DeleteMapping(path = "/{userId}")
    public Result deleteUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }

    @PutMapping(path = "/{userId}")
    public Result updateUser(@PathVariable(name = "userId") Long userId) {

        return Result.ok();
    }

    @RequestMapping(path = "/query/{userId}",method = RequestMethod.GET)
    public ModelAndView queryUser(@PathVariable(name = "userId") Long userId, String keywords) {
        List<User> userList = userService.queryUser(userId, keywords);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
