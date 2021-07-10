package org.xr.happy.junit.springboot.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.xr.happy.common.dto.City;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.model.User;
import org.xr.happy.service.CityService;
import org.xr.happy.service.impl.CityServiceImpl;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
    @InjectMocks
    private CityService cityService = new CityServiceImpl();

    @Mock
    private UserMapper userMapper;

    @Test
    public void testDao() {

        User user = new User();
        user.setUsername("steven");
        List<City> allCities = cityService.getAllCities();
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        Assert.assertEquals(1, users.size());
    }

}
