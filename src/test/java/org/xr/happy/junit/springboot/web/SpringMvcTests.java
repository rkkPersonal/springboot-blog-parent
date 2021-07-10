package org.xr.happy.junit.springboot.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.xr.happy.controller.ApiController;
import org.xr.happy.controller.CityController;
import org.xr.happy.controller.DemoController;
import org.xr.happy.service.CityService;
import org.xr.happy.service.impl.CityServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringMvcTests {

    private MockMvc mockMvc;


    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CityController(), new DemoController(), new ApiController()).build();
    }

    @Test
    public void getUserById() throws Exception {
        long id = 1;
        this.mockMvc.perform(get("/cities/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("kkkk"));
        this.mockMvc.perform(get("/api/demo/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("aaa"));
    }

}
