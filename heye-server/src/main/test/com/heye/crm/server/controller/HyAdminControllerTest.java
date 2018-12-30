package com.heye.crm.server.controller;

import com.heye.crm.common.consts.Consts;
import com.heye.crm.server.HeYeServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class HyAdminControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testLoginHyUserByPasswd() throws Exception {
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

    @Test
    public void testGetRoleList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getRoleList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getRoleList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"wrong_session_id\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }

    @Test
    public void testGetRightList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getRightList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getRightList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"wrong_session_id\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }

    @Test
    public void testGetHyUserList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getHyUserList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getHyUserList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"wrong_session_id\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }

    @Test
    public void testGetParentRoleAdmins() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getParentRoleAdmins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"roleId\":4, \"userParentName\":\"admin\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getParentRoleAdmins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"wrong_session_id\", \"userId\":1, \"roleId\":4, \"userParentName\":\"admin\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }

    @Test
    public void testInsertIntoHyUser() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/insertIntoHyUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"userName\":\"李书明\", \"roleId\":4, \"userParentName\":\"喜洋洋\",\"userParentId\":4,\"userAccountName\":\"李书明\", \"userSex\":0, \"userTelephone\":\"18210287328\", \"userProvince\":\"浙江\", \"userCity\":\"杭州\", \"userLocation\":\"滨江\",\"userDetailAddress\":\"xxx\",\"userIdentity\":\"1234567889900032\", \"userRangeType\":0}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/insertIntoHyUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"userName\":\"李书明\", " +
                        "\"roleId\":4, \"userParentName\":\"喜洋洋\",\"userParentId\":4,\"userAccountName\":\"李书明\"," +
                        " \"userSex\":0, \"userTelephone\":\"18210287328\", \"userProvince\":\"浙江\", \"userCity\":\"杭州\"," +
                        " \"userLocation\":\"滨江\",\"userDetailAddress\":\"xxx\",\"userIdentity\":\"1234567889900032\"," +
                        " \"userRangeType\":0}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("already_existed_in_db"))
                .andDo(print());

    }

    @Test
    public void testUpdateUserPasswd() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/updateUserPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originPasswd\":\"a\", \"userPasswd\":\"admin\", \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/updateUserPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originPasswd\":\"a\", \"userPasswd\":\"admin\", \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("ORIGIN_PASSWD_WRONG".toLowerCase()))
                .andDo(print());

    }

    @Test
    public void testResetAdminPasswd() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/resetAdminPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/resetAdminPasswd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"wrong_session_id\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }


    @Test
    public void testGetUserDetailById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getUserDetailById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/getUserDetailById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"wrong_session_id\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());

    }

    @Test
    public void testSearchAccount() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/searchAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":3, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"realName\":\"李书明\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/searchAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":3, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"locationName\":\"滨江\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/searchAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":3, \"sessionId\":\"5c0f55a504364b9a925002eada6ff7d1\", \"userId\":1, \"realName\":\"wrong_name\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.NO_SUCH_OBJECT))
                .andDo(print());

    }

    @Test
    public void testUpdateHyUserById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/updateHyUserById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"ba13e60f0c324eed9d59690c662977a8\", \"userId\":1, \"userState\":1, " +
                        "\"userName\":\"admin\", \"roleId\":1, \"userParentName\":\"喜洋洋\",\"userParentId\":4," +
                        "\"userAccountName\":\"李书明\", \"userSex\":0, \"userTelephone\":\"18210287328\"," +
                        " \"userProvince\":\"浙江\", \"userCity\":\"杭州\", \"userLocation\":\"滨江\"," +
                        "\"userDetailAddress\":\"xxx\",\"userIdentity\":\"1234567889900032\", \"userRangeType\":0}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/updateHyUserById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"ba13e60f0c324eed9d59690c662977a8\", \"userId\":1, \"userState\":1, " +
                        "\"userName\":\"admin\", \"roleId\":1, \"userParentName\":\"喜洋洋\",\"userParentId\":4," +
                        "\"userAccountName\":\"李书明\", \"userSex\":0, \"userTelephone\":\"18210287328\"," +
                        " \"userProvince\":\"浙江\", \"userCity\":\"杭州\", \"userLocation\":\"滨江\"," +
                        "\"userDetailAddress\":\"xxx\",\"userIdentity\":\"1234567889900032\", \"userRangeType\":0}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value(Consts.INVALID_PARAM))
                .andDo(print());

    }


}
