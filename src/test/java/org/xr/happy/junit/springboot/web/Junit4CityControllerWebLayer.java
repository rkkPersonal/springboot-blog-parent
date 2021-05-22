package org.xr.happy.junit.springboot.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.xr.happy.common.dto.City;
import org.xr.happy.controller.CityController;
import org.xr.happy.service.CityService;

import java.util.Collections;

/**
 * junit  4 测试
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
@AutoConfigureMockMvc
public class Junit4CityControllerWebLayer {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityService service;

    @Test
    public void getAllCities() throws Exception {

        City city = new City();
        city.setId(1L);
        city.setName("杭州");
        city.setState("浙江");
        city.setCountry("中国");

        Mockito.when(service.getAllCities()).thenReturn(Collections.singletonList(city));

        mvc.perform(MockMvcRequestBuilders.get("/cities"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("杭州")));
    }
}
