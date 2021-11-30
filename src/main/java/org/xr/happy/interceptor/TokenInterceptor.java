package org.xr.happy.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.xr.happy.common.annotation.Permission;
import org.xr.happy.common.dto.Result;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.UserService;
import org.xr.happy.service.impl.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      /*  String username = request.getHeader("token");
        logger.info("token is :{}", username);
        if (StringUtils.isNotBlank(username)) {
            User user = new User();
            user.setUsername(username);
            List<String> userAuthorized = userService.getUserAuthorized(user);

            if (handler instanceof HandlerMethod) {
                HandlerMethod h = (HandlerMethod) handler;
                Method method = h.getMethod();
                if (method.isAnnotationPresent(Permission.class)) {
                    Permission permission = method.getAnnotation(Permission.class);
                    String[] permissions = permission.permissions();
                    for (String s : permissions) {
                        if (userAuthorized.contains(s)) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }

        responseBody(response);*/
        return true;
    }

    private void responseBody(HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        Result error = Result.error("没有权限访问!");
        String string = JSON.toJSONString(error);
        writer.write(string);
        writer.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
