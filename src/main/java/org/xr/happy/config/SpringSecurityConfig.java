package org.xr.happy.config;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.xr.happy.filter.ValidatorFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven
 */
@Configuration
@Order(1)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * settings the authorization basic header  username and password
         */
        auth.inMemoryAuthentication()
                .withUser("steven")
                .password(passwordEncoder().encode("123456"))
                .roles("user");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Allow front end invoke cookies
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
            ValidatorFilter is executed before user password filter, in order to achieve the customize verify
            http basic is  for request add header authorization basic xxxxx
         */
        http.addFilterBefore(new ValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic()
                .and().authorizeRequests()
                .and()
                .authorizeRequests()
                .antMatchers("/signIn").permitAll()
                .and().authorizeRequests()      //开启登录选择认证
                .antMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
               // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .csrf().disable();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
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
        return corsSource;
    }
}
