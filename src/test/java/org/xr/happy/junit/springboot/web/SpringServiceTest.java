package org.xr.happy.junit.springboot.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.xr.happy.common.dto.City;
import org.xr.happy.controller.CityController;
import org.xr.happy.mapper.UserMapper;
import org.xr.happy.service.CityService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringServiceTest {

    MockMvc mockMvc;
    @InjectMocks
    CityController cityController;

    @Mock
    CityService cityService;

    @Mock
    UserMapper userMapper;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    public void getUserById() throws Exception {
        long id = 1L;
        String ricky = "Ricky";
        Mockito.when(cityService.getAllCities()).thenReturn(new ArrayList<City>());
        this.mockMvc.perform(get("/user/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }


    @Test
    public  void hello() {
/*

        String js = "[[\"hell0=1\",\"hi=2\"],[\"steven=3\",\"sx=4\"],[\"james=5\",\"peck=6\"]]";


        JSONArray jsonArray = JSON.parseArray(js);


        for (Object o : jsonArray) {

            System.out.println(o.toString());
            JSONArray jsonArray1 = JSON.parseArray(o.toString());

            for (Object o1 : jsonArray1) {

                //System.out.println(o1.toString());
            }



        }
*/


        String ls = "hell=1,hi=2,hsi=3,hks=4";

        String[] split = ls.split(",");


        Map<String, String> collect = Arrays.stream(split).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));

        System.out.println(collect.toString());
    }


}
