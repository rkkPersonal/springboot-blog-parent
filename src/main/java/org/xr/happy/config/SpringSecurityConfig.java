package org.xr.happy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.xr.happy.filter.ValidatorFilter;

/**
 * @author Steven
 */
@Configuration
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
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
