package org.xr.happy.mvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.xr.happy.common.vo.UserVo;
import org.xr.happy.junit.springboot.web.Junit4ControllerTest;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class MvcTest {

    @Autowired
    private WebApplicationContext web;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(web).build();
    }

    @Test
    public void testJsonView() throws Exception {
        UserVo userVo = new UserVo();
        userVo.setUsername("james");
        userVo.setEmail("17333323@gmail.com");
        String resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/test/valid")
                        .content(Junit4ControllerTest.asJsonString(userVo))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(resultActions);
    }
}
