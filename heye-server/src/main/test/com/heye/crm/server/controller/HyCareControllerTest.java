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
public class HyCareControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetCustomCareList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/getCustomCareList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/getCustomCareList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":-1, \"pageSize\":-10}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("getCustomCareList fail: parameter invalid."))
                .andDo(print());
    }

    @Test
    public void testAddCustomCare() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/addCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"festName\":\"国庆节\", \"festDate\":\"2018-10-01\", \"sendTime\":\"10:00\"," +
                        "\"sendDesc\":\"亲爱的和也用户，祝您五一节快乐\", \"ctmRangeType\":0, \"ctmSex\":0, " +
                        "\"ageRange\":\"40-50\", \"consumeRange\":\"1000-10000\",\"switchSate\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/addCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"festDate\":\"2018-10-01\", \"sendTime\":\"10:00\"," +
                        "\"sendDesc\":\"亲爱的和也用户，祝您五一节快乐\", \"ctmRangeType\":0, \"ctmSex\":0, " +
                        "\"ageRange\":\"40-50\", \"consumeRange\":\"1000-10000\",\"switchSate\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_param"))
                .andDo(print());
    }

    @Test
    public void testUpdateCustomCare() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/updateCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"careId\":12, " +
                        "\"festName\":\"国庆2节\", \"festDate\":\"2018-10-01\", \"sendTime\":\"10:00\"," +
                        "\"sendDesc\":\"亲爱的和也用户，祝您国庆节快乐\", \"ctmRangeType\":0, \"ctmSex\":0, " +
                        "\"ageRange\":\"40-50\", \"consumeRange\":\"1000-10000\",\"switchSate\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/updateCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"careId\":13333, " +
                        "\"festName\":\"国庆2节\", \"festDate\":\"2018-10-01\", \"sendTime\":\"10:00\"," +
                        "\"sendDesc\":\"亲爱的和也用户，祝您国庆节快乐\", \"ctmRangeType\":0, \"ctmSex\":0, " +
                        "\"ageRange\":\"40-50\", \"consumeRange\":\"1000-10000\",\"switchSate\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("The care doesn't exist!"))
                .andDo(print());
    }

    @Test
    public void testDeleteCustomCare() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/deleteCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"careId\":12}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/care/deleteCustomCare")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"careId\":12222222}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("The care doesn't exist!"))
                .andDo(print());
    }
}
