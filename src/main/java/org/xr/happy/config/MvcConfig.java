//package org.xr.happy.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.xr.happy.interceptor.TokenInterceptor;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//    @Resource
//    private TokenInterceptor myInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 设置接口只有携带token才可以访问的路劲
//        registry.addInterceptor(myInterceptor).addPathPatterns("/token/**");
//    }
//}