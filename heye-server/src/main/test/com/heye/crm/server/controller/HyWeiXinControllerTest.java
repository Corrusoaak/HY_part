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
public class HyWeiXinControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testGetWeiXinSign() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/getWeiXinSign")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void testWxLogin() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/wxLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"*********************\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/wxLogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"***********************1234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg")
                        .value("getSessionKeyOropenid fail: weiXinCode is null!"))
                .andDo(print());
    }

    @Test
    public void testBindTelephoneNum() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/bindTelephoneNum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"***********************1234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/bindTelephoneNum")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"***********************1234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg")
                        .value("getSessionKeyOropenid fail: weiXinCode is null!"))
                .andDo(print());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/updateCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":16, \"ctmName\":\"更新测试\", \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
                        "\"admin\", \"ctmSex\":0, \"ctmEmail\":\"lism@heye.com\", \"ctmPicture\":\"浙江\", " +
                        "\"ctmPhoneNum\":\"18210282901\", \"ctmTelephone\":\"84923104891230\", " +
                        "\"ctmBirthDay\":\"1991-04-01\", \"ctmProvince\":\"河南\", \"managerName\":\"admin\", " +
                        "\"ctmLocation\":\"浙江区\", \"ctmDetailAddress\":\"滨江区xxx\", \"ctmZipCode\":\"00010\", " +
                        "\"ctmIdentity\":\"12212123\", \"createdAt\":\"2018-04-06\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/updateCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":16, \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
                        "\"admin\", \"ctmSex\":0, \"ctmEmail\":\"lism@heye.com\", \"ctmPicture\":\"浙江\", " +
                        "\"ctmPhoneNum\":\"18210282901\", \"ctmTelephone\":\"84923104891230\", " +
                        "\"ctmBirthDay\":\"1991-04-01\", \"ctmProvince\":\"河南\", \"managerName\":\"admin\", " +
                        "\"ctmLocation\":\"浙江区\", \"ctmDetailAddress\":\"滨江区xxx\", \"ctmZipCode\":\"00010\", " +
                        "\"ctmIdentity\":\"12212123\", \"createdAt\":\"2018-04-06\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg")
                        .value("invalid_param"))
                .andDo(print());
    }

    @Test
    public void testSendPhoneIdentifyCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/weixin/sendPhoneIdentifyCode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmPhoneNum}\":\"18867137889\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg")
                        .value("123"))
                .andDo(print());
    }
}
