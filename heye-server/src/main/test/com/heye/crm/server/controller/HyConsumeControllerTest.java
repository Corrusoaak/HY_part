package com.heye.crm.server.controller;

import com.heye.crm.server.HeYeServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author : lishuming
 * @refer : https://github.com/gustavoponce7/SpringBootUnitTestTutorial
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HeYeServerApplication.class)
@WebAppConfiguration
public class HyConsumeControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testApi() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/loginHyUserByPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"admin\", \"userPasswd\":\"admin\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/loginHyUserByPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"admin\", \"userPasswd\":\"wrong_password\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("not_found"))
                .andDo(print());
    }
}
