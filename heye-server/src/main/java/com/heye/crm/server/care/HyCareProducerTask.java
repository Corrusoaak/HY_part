package com.heye.crm.server.care;

import com.alibaba.fastjson.JSON;
import com.google.common.annotations.VisibleForTesting;
import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyCare;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.utils.RedisClient;
import com.heye.crm.server.service.HyCareService;
import com.heye.crm.server.service.HyCustomerService;
import com.heye.crm.server.service.impl.HyCareServiceImpl;
import com.heye.crm.server.service.impl.HyCustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : lishuming
 */
public class HyCareProducerTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyCareProducerTask.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Jedis jedis = null;

    static {
        try {
            jedis = new RedisClient().getJedis();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            jedis.set("date", format.format(date));
        } catch (Exception e) {
            LOGGER.warn("redis init failed: ", e);
        }
    }

    private String taskName;
    private HyCareService hyCareService;
    private HyCustomerService hyCustomerService;

    public HyCareProducerTask(String name) {
        this.taskName = name;
    }

    @VisibleForTesting
    void addMessage(String message) {
        try {
            LOGGER.info("add message :" + message);
            jedis.lpush(Consts.CTM_CARE_TASK_QUEUE_NAME, message);
        } catch (Exception e) {
            LOGGER.warn("add message failed:", e);
        }
    }

    public void run() {
        LOGGER.info("start to produce task...");
        hyCareService = new HyCareServiceImpl();
        hyCustomerService = new HyCustomerServiceImpl();

        // 获取数据库中的节日列表
        // TODO: 根据节日列表对应的条件，筛选对应的客户群体
        List<HyCare> list = hyCareService.getSchedulerCareList();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        for (HyCare hyCare : list) {
            LOGGER.info("Start to check festival: " + hyCare.getFestName());
            List<HyCustomer> hyCustomers = new ArrayList<>();
            if (hyCare.getSendState() == 1) {
                continue;
            }
            HyQMessage msg = new HyQMessage();
            msg.setMsgContext(hyCare.getSendDesc());
            msg.setMsgSendTime(hyCare.getSendTime());
            msg.setScheduleTime(DATE_FORMAT.format(new Date(System.currentTimeMillis())));

            String sendTime = hyCare.getSendTime();
            if (!hyCare.getFestName().equals("客户生日")) {
                String festDate = format.format(hyCare.getFestDate());
                if (currentTime.after(Timestamp.valueOf(festDate + " " + sendTime + ":00"))) {
                    if (hyCare.getCtmRangeType() == 0) {
                        hyCustomers = hyCustomerService.findAll();
                    } else {
                        if (!"".equals(hyCare.getAgeRange())) {
                            String[] ages = hyCare.getAgeRange().split("-");
                            int lowerAge = Integer.valueOf(ages[0]);
                            int upperAge = Integer.valueOf(ages[1]);
                            hyCustomers = hyCustomerService.findCustomerByRange(hyCare.getCtmSex(),
                                    hyCare.getAgeRange(), lowerAge, upperAge, null);
                        } else {
                            hyCustomers = hyCustomerService.findCustomerByRange(hyCare.getCtmSex(),
                                    null, 0, 0, new Date());
                        }
                    }
                    hyCare.setSendState(1);
                    hyCareService.updateNotDefault(hyCare);
                }
            } else {
                String curDate = format.format(new Date());
                if (currentTime.after(Timestamp.valueOf(curDate + " " + sendTime + ":00"))) {
                    if (hyCare.getCtmRangeType() == 0) {
                        hyCustomers = hyCustomerService.findCustomerByBirth(new Date());
                    } else {
                        if (!"".equals(hyCare.getAgeRange())) {
                            String[] ages = hyCare.getAgeRange().split("-");
                            int lowerAge = Integer.valueOf(ages[0]);
                            int upperAge = Integer.valueOf(ages[1]);
                            hyCustomers = hyCustomerService.findCustomerByRange(hyCare.getCtmSex(),
                                    hyCare.getAgeRange(), lowerAge, upperAge, new Date());
                        } else {
                            hyCustomers = hyCustomerService.findCustomerByRange(hyCare.getCtmSex(),
                                    null, 0, 0, new Date());
                        }
                    }
                    hyCare.setSendState(1);
                    hyCareService.updateNotDefault(hyCare);
                }
            }
            for (HyCustomer hyCustomer : hyCustomers) {
                msg.setMsgPhoneNum(hyCustomer.getCtmPhoneNum());
                String redisV = JSON.toJSONString(msg);
                addMessage(redisV);
            }
        }

        try {
            Date lastDay = format.parse(jedis.get("date"));
            calendar.setTime(lastDay);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date newDay = new Date();
            if (newDay.compareTo(calendar.getTime()) == 0) {
                jedis.append("date", format.format(newDay));
                for (HyCare hyCare : list) {
                    if (hyCare.getFestName().equals("客户生日")) {
                        hyCare.setSendState(0);
                        hyCareService.updateNotDefault(hyCare);
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
