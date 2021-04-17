package org.xr.happy.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.xr.happy.common.dto.City;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.service.CityService;
import org.xr.happy.service.impl.CityServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class ServiceTest {

    @Mock
    private CityServiceImpl cityService;
    @Mock
    private UserMapper userMapper;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test(){


        List<City> list = new ArrayList<>();
        Collections.addAll(list, new City("A", 1L, "杭州", "浙江", "中国"),
                new City("B", 2L, "海宁", "浙江", "中国"));

        Mockito.when(cityService.getAllCities()).thenReturn(list);

        Mockito.when(cityService.findCityByCountCode("steven")).thenReturn(list);


        List<City> steven = cityService.findCityByCountCode("steven");

        Assert.assertEquals(list.size(),2);

        Assert.assertEquals(steven.size(),list.size());
    }



}
