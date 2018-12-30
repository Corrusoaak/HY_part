package com.heye.crm.server.care;

import com.alibaba.fastjson.JSON;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.utils.RedisClient;
import com.heye.crm.common.utils.SmsApi;
import com.heye.crm.common.utils.SmsApiResult;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

/**
 * @author : lishuming
 */
public class HyCareQueueThreadTest {
    HyCareQueueService hyCareQueueService = new HyCareQueueService();

    @Test
    public void testSendSMS() {
        String text = SmsApi.HY_SMS_DEFAULT_LABEL + "您的验证码是123456。如非本人操作，请忽略本短信";

        SmsApiResult res = SmsApi.sendSms(text, "18867137889");
        System.out.println(res.toString());
        if (res == null || res.getCode() != 0) {
            System.out.println("发送失败");
        } else {
            System.out.println("发送成功");
        }
    }

    @Test
    public void testService() {
        showAndClearRedisList();

        hyCareQueueService.startProducer();
        hyCareQueueService.startConsumer();

        try {
            Thread.sleep(10000);

            hyCareQueueService.awaitCompute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRedis() {
        Jedis jedis = new RedisClient().getJedis();
        {
            long size = jedis.llen(Consts.CTM_CARE_TASK_QUEUE_NAME);
            System.out.println("size = " + size);
        }
    }

    @Test
    public void showAndClearRedisList() {
        Jedis jedis = new RedisClient().getJedis();
        long size;

        size = jedis.llen(Consts.CTM_CARE_TASK_QUEUE_NAME);
        System.out.println("CTM_CARE_QUEUE_PREFIX_KEY size = " + size);
        for (int i = 0; i < size; i++) {
            String s = jedis.rpop(Consts.CTM_CARE_TASK_QUEUE_NAME);
            System.out.println("element=" + s);
        }
        size = jedis.llen(Consts.CTM_CARE_TASK_QUEUE_NAME);
        System.out.println("After clear, size = " + size);

        size = jedis.llen(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
        System.out.println("CTM_CARE_QUEUE_BAK size = " + size);
        for (int i = 0; i < size; i++) {
            String s = jedis.rpop(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
            System.out.println("element=" + s);
        }
        size = jedis.llen(Consts.CTM_CARE_TASK_QUEUE_NAME);
        System.out.println("After clear, size = " + size);
    }

    @Test
    public void testProducer(){

    }

}
