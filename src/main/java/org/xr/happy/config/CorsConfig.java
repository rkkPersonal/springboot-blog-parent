package org.xr.happy.config;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        List<String> list=new ArrayList<>();
        CollectionUtils.addAll(list,"http://localhost:8080","http://localhost:8081","http://127.0.0.1:8081","http://steven.study.com","http://localhost:3000");
        config.setAllowedOrigins(list);

        // 用来指定本次预检请求的有效期，单位为秒，在有效期间，不用发出另一条预检请求
        // 如果发现每次发起请求都是两条，一次Options，一次正常请求，那么需要配置 Access-Control-Max-Age，避免每次都发出预检请求
        config.setMaxAge(3600L);

        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 设置允许请求的方式
        config.addAllowedMethod("*");

        // 设置允许的header
        config.addAllowedHeader("*");

        // 2. 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        // 3. 返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(corsFilter());
        bean.setOrder(0);
        return bean;
    }
}
