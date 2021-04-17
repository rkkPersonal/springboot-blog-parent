package org.xr.happy.junit.springboot.web;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xr.happy.controller.CityController;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.CityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * junit 4 ---org.junit
 * junit 5 --- org.junit.jupiter.api
 */
@ExtendWith(MockitoExtension.class)
public class Junit5ControllerTest {

    @InjectMocks
    private CityController cityController;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CityService cityService;

    @Test
    public void testController() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));


        User user = new User();
        user.setUsername("steven");
        when(userMapper.selectOne(any(User.class))).thenReturn(user);

        // when
        ResponseEntity<?> allCities = cityController.getAllCities();


        // then
        assertThat(allCities.getStatusCodeValue()).isEqualTo(200);


    }


    @Test
    public void test() {


        Assert.assertEquals(10, calculator(10));
    }

    public static int calculator(int num) {

        return num;

    }
}
