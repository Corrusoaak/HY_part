package com.heye.crm.server.analyze;

import com.heye.crm.common.utils.RedisClient;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.JobBuilder.newJob;

/**
 * @author : lishuming
 */
public class HyAnalyzeMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAnalyzeMain.class);
    private static final boolean TEST = true;

    public static void main(String[] args) {
        if (TEST) {
            Jedis jedis = new RedisClient().getJedis();
            jedis.del("analyze_daily_date");
            jedis.del("analyze_monthly_date");
            jedis.del("analyze_yearly_date");
        }

        // 基于quartz来实现

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 1. 每天调度
            Trigger trigger = newTrigger().withIdentity("dailyTrigger", "dailyGroup") //定义name/group
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(simpleSchedule() //使用SimpleTrigger
                            .withIntervalInSeconds(10)
                            //.withIntervalInHours(24) //每隔24小时
                            .repeatForever()) //一直执行，奔腾到老不停歇
                    .build();

            JobDetail job = newJob(HyAnalyzeDailyQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity("dailyTrigger", "dailyGroup") //定义name/group
                    .build();

            scheduler.scheduleJob(job, trigger);

            // 2. 每月调度
            trigger = newTrigger().withIdentity("monthlyTrigger", "monthlyGroup") //定义name/group
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(simpleSchedule() //使用SimpleTrigger
                            .withIntervalInSeconds(10)
                            //.withIntervalInHours(24) //每隔24小时
                            .repeatForever()) //一直执行，奔腾到老不停歇
                    .build();

            job = newJob(HyAnalyzeMonthlyQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity("monthlyTrigger", "monthlyGroup") //定义name/group
                    .build();

            scheduler.scheduleJob(job, trigger);

            // 3. 每年调度
            trigger = newTrigger().withIdentity("yearlyTrigger", "yearlyGroup") //定义name/group
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(simpleSchedule() //使用SimpleTrigger
                            .withIntervalInSeconds(10)
                            //.withIntervalInHours(24) //每隔24小时
                            .repeatForever()) //一直执行，奔腾到老不停歇
                    .build();

            job = newJob(HyAnalyzeYearlyQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity("yearlyTrigger", "yearlyGroup") //定义name/group
                    .build();

            scheduler.scheduleJob(job, trigger);

            //启动之
            scheduler.start();

            //scheduler.shutdown(true);
        } catch (Exception e) {
            LOGGER.warn("start to analyze scheduler failed:", e);
        }
    }
}
