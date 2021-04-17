package org.xr.happy.junit.springboot.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.xr.happy.common.dto.City;
import org.xr.happy.controller.CityController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * junit 4 测试
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class Junit4ControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testDao() {

    }

    @Test
    public void testService() {

        try {
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/city/{id}", 2)
                    .content(asJsonString(new City("飞机", 110L, "steven", "上海", "中国")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.traffic").value("飞机"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("110"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.state").value("上海"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
