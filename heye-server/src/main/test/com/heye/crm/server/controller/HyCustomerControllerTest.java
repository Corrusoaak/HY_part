package com.heye.crm.server.controller;

import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.utils.ResultCode;
import com.heye.crm.common.utils.SmsApi;
import com.heye.crm.common.utils.SmsApiResult;
import com.heye.crm.server.HeYeServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author : lishuming
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HeYeServerApplication.class)
@WebAppConfiguration
public class HyCustomerControllerTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HyCustomerControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetHyCustomerList() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomerList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"44ccdfdaa59b40ac931ddfd0d1f37f38\", " +
                        "\"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomerList")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"sessionId\":\"ccdfdaa59b40ac931ddfd0d1f37f38\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());
    }

    @Test
    public void testInsertIntoHyCustom() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/insertIntoHyCustom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmName\":\"写入测试\", \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/insertIntoHyCustom")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmName\":\"写入测试\", \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
                        "\"admin\", \"ctmSex\":0, \"ctmEmail\":\"lism@heye.com\", \"ctmPicture\":\"浙江\", " +
                        "\"ctmPhoneNum\":\"18210282901\", \"ctmTelephone\":\"84923104891230\", " +
                        "\"ctmBirthDay\":\"1991-04-01\", \"ctmProvince\":\"河南\", \"managerName\":\"admin\", " +
                        "\"ctmLocation\":\"浙江区\", \"ctmDetailAddress\":\"滨江区xxx\", \"ctmZipCode\":\"00010\", " +
                        "\"ctmIdentity\":\"12212123\", \"createdAt\":\"2018-04-06\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("CUSTOMER_ALREADY_EXISTED"))
                .andDo(print());
    }

    @Test
    public void testLogin() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmTelephone\":\"123456789\", \"ctmPasswd\":\"admin01\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmTelephone\":\"123456789\", \"ctmPasswd\":\"aaaaaaaaaa\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("NO_SUCH_USER"))
                .andDo(print());
    }

    @Test
    public void testDeleteHyCustomById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/deleteHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":\"13\", \"sessionId\":\"44ccdfdaa59b40ac931ddfd0d1f37f38\",\"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/deleteHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":\"13\", \"sessionId\":\"44ccdfdaa59b40ac931ddfd0d1f37f38\",\"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("NO_SUCH_CUSTOM"))
                .andDo(print());
    }

    @Test
    public void TestBatchDeleteHyCustomById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/batchDeleteHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmIds\":\"13,14,15\", \"sessionId\":\"44ccdfdaa59b40ac931ddfd0d1f37f38\",\"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/batchDeleteHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmIds\":\"13,14,15\", \"sessionId\":\"44ccdfdaa59b40ac931ddfd0d1f37f38\",\"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("NO_SUCH_CUSTOM"))
                .andDo(print());
    }

    @Test
    public void TestGetHyCustomById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"ctmId\":1, " +
                        "\"sessionId\":\"ab16209f083845c8b079d9c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"ctmId\":1, " +
                        "\"sessionId\":\"083845c8b079d9c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());
    }

    @Test
    public void TestGetHyCustomByDateType() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomByDateType")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"filterByDateType\":1, " +
                        "\"sessionId\":\"ab16209f083845c8b079d9c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomByDateType")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"filterByDateType\":1, " +
                        "\"sessionId\":\"c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());
    }

    @Test
    public void TestSearchByManagerNameOrDateType() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/searchByManagerNameOrDateType")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"managerName\":\"admin\", " +
                        "\"sessionId\":\"ab16209f083845c8b079d9c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/searchByManagerNameOrDateType")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"managerName\":\"admin\", " +
                        "\"sessionId\":\"c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());
    }

    @Test
    public void TestGetHyCustomByManagerName() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomByManagerName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"managerName\":\"admin\", " +
                        "\"sessionId\":\"ab16209f083845c8b079d9c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.msg").value("SUCCESS"))
                .andDo(print());

        // 测试异常
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/getHyCustomByManagerName")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pageNo\":1, \"pageSize\":10, \"managerName\":\"admin\", " +
                        "\"sessionId\":\"c802d9628a\", \"userId\":1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("invalid_session"))
                .andDo(print());
    }

    @Test
    public void TestUpdateHyCustomById() throws Exception {
        // 测试正常逻辑
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/updateHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":16, \"ctmName\":\"写入测试\", \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/updateHyCustomById")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ctmId\":1226, \"ctmName\":\"写入测试\", \"storeId\":1, \"ctmAccountName\":\"test2\", \"ctmPasswd\":" +
                        "\"admin\", \"ctmSex\":0, \"ctmEmail\":\"lism@heye.com\", \"ctmPicture\":\"浙江\", " +
                        "\"ctmPhoneNum\":\"18210282901\", \"ctmTelephone\":\"84923104891230\", " +
                        "\"ctmBirthDay\":\"1991-04-01\", \"ctmProvince\":\"河南\", \"managerName\":\"admin\", " +
                        "\"ctmLocation\":\"浙江区\", \"ctmDetailAddress\":\"滨江区xxx\", \"ctmZipCode\":\"00010\", " +
                        "\"ctmIdentity\":\"12212123\", \"createdAt\":\"2018-04-06\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.msg").value("The customer doesn't exist!"))
                .andDo(print());
    }

    /**
     * testGetCustomerByDate
     */
    @Test
    public void testGetCustomerByDate() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String temp = dateFormat.format(calendar.getTime());
        String thisDayString = temp + " 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(thisDayString);
        System.out.println(timestamp);

        int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        weekday = weekday == 0 ? 7 : weekday;
        System.out.println(weekday);
        long dateTime = System.currentTimeMillis() - (weekday - 1) * 86400000;
        Date date = new Date(dateTime);
        String thisWeekString = dateFormat.format(date) + " 00:00:00";
        timestamp = Timestamp.valueOf(thisWeekString);
        System.out.println(timestamp);

        String thisMonthString = temp.substring(0, 7) + "-01 00:00:00";
        timestamp = Timestamp.valueOf(thisMonthString);
        System.out.println(timestamp);
    }

    @Test
    public void testSMS() {
        String text = "【和也健康科技】您的验证码是1234";
        SmsApiResult res = SmsApi.sendSms(text, "18867137889");
        LOGGER.info(res.toString());
    }
}
