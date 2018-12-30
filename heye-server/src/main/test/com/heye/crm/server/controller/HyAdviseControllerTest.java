package com.heye.crm.server.controller;

import com.heye.crm.common.consts.Consts;
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
public class HyAdviseControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testAddAdviseToPro() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/addAdviseToPro")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":1, \"proId\":1, \"adviseType\":1, \"adviseDesc\":\"投诉描述\", \"advisePictures\":\"照片，可选\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/addAdviseToPro")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":1, \"proId\":1, \"adviseType\":1, \"adviseDesc\":\"\", \"advisePictures\":\"照片，可选\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());
    }

    @Test
    public void testAdviseList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/adviseList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"ctmId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/adviseList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"ctmId\":-1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());
    }

    @Test
    public void testGetAdviseByID() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/getAdviseByID")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviseId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/getAdviseByID")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviseId\":-1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());
    }

    @Test
    public void testReplyAdvise() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/replyAdvise")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviseId\": 2, \"aviseReplies\":\"ok\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/replyAdvise")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviseId\": 3, \"aviseReplies\":\"ok\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.NOT_EXISTED_IN_DB))
                .andDo(print());
    }

    @Test
    public void testUpdateAdvise() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/updateAdvise")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"adviseState\": 2, \"adviseId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/updateAdvise")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"adviseState\": 4, \"adviseId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());
    }

    @Test
    public void testSearchAdviseList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/searchAdviseList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviceState\":0, \"filterByDateType\":1, \"adviseType\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/advise/searchAdviseList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"adviceState\":0, \"filterByDateType\":1, \"adviseType\":3}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());
    }

}
