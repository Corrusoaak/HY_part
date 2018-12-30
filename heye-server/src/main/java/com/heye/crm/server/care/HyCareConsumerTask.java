package com.heye.crm.server.care;

import com.alibaba.fastjson.JSON;
import com.google.common.annotations.VisibleForTesting;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.utils.RedisClient;
import com.heye.crm.common.utils.SmsApi;
import com.heye.crm.common.utils.SmsApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lishuming
 */
public class HyCareConsumerTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyCareConsumerTask.class);
    private final Jedis jedis = new RedisClient().getJedis();
    private final boolean forTest = true;

    /**
     * TODO: 该方法会重复消费，怎么处理？
     *
     * @return
     */
    @VisibleForTesting
    protected List<HyQMessage> getMessage() {
        List<HyQMessage> messages = new ArrayList<>();
        int i = 0;
        try {
            while (i < Consts.CTM_CARE_QUEUE_BATCH_SIZE) {
                String message = jedis.rpoplpush(Consts.CTM_CARE_TASK_QUEUE_NAME, Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
                HyQMessage hyQMessage = JSON.parseObject(message, HyQMessage.class);
                messages.add(hyQMessage);
                i++;
            }
            /**
             List<String> list = jedis.lrange(Consts.CTM_CARE_TASK_QUEUE_NAME, 0, -1);
             for (String v : list) {
             LOGGER.info("get key:" + v);
             //TODO: check message

             HyQMessage hyQMessage = JSON.parseObject(v, HyQMessage.class);
             messages.add(hyQMessage);
             }*/
            //String v = jedis.lpop("test");
        } catch (Exception e) {

        }
        return messages;
    }

    public void run() {
        LOGGER.info("start to consume task...");

        /**
         int i = 0;
         while (i < Consts.CTM_CARE_QUEUE_BATCH_SIZE) {
         try {
         String message = jedis.rpoplpush(Consts.CTM_CARE_TASK_QUEUE_NAME, Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
         if (message == null) {
         LOGGER.info("queue empty");
         break;
         }

         HyQMessage hyQMessage = JSON.parseObject(message, HyQMessage.class);

         // TODO: 消费redis队列，发送短信
         int result = 1;

         if (result == 1) {
         LOGGER.info("handle message succeed:", hyQMessage.getMsgPhoneNum());
         // 短信发送成功，从备份队列中删除原消息
         jedis.rpop(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
         } else {
         LOGGER.warn("handle message failed, redo :", hyQMessage.getMsgPhoneNum());
         // 否则，从备份队列中恢复至原队列中
         jedis.rpoplpush(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME, Consts.CTM_CARE_TASK_QUEUE_NAME);
         }
         } catch (Exception e) {
         LOGGER.warn("handle message failed:", e);
         }
         }*/

        int i = 0;
        while (i < Consts.CTM_CARE_QUEUE_BATCH_SIZE) {
            // 阻塞读取redis中的共享队列
            String redisV = jedis.brpoplpush(Consts.CTM_CARE_TASK_QUEUE_NAME,
                    Consts.CTM_CARE_TASK_TMP_QUEUE_NAME, 0);
            LOGGER.info("start to handle message:" + redisV);
            HyQMessage msg = JSON.parseObject(redisV, HyQMessage.class);
            if (msg == null) {
                // 读取失败，message从备份队列放回共享队列
                jedis.rpoplpush(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME, Consts.CTM_CARE_TASK_QUEUE_NAME);
                continue;
            }

            // 消费redis队列，发送短信
            try {
                SmsApiResult result = SmsApi.sendSms(SmsApi.HY_SMS_DEFAULT_LABEL + msg.getMsgContext(),
                        msg.getMsgPhoneNum());

                LOGGER.info("SmsApiResult=" + result);
                if (!forTest && result.getCode() != 0) { // code != 0 说明发送失败
                    // 发送失败，message从备份队列放回共享队列
                    jedis.rpoplpush(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME,
                            Consts.CTM_CARE_TASK_QUEUE_NAME);
                    LOGGER.warn("Send fail: return code = " + result.getCode());
                } else {
                    // 发送成功，pop出备份
                    jedis.rpop(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME);
                }
            } catch (Exception e) {
                // 如果发送短信失败了，message会从备份队列里重新放回共享队列中
                jedis.rpoplpush(Consts.CTM_CARE_TASK_TMP_QUEUE_NAME,
                        Consts.CTM_CARE_TASK_QUEUE_NAME);
                LOGGER.warn("Send fail: " + msg.toString());
            }

            i++;
        }

        LOGGER.info("batch done!");
    }
}
