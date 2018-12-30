package com.heye.crm.care;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author : lishuming
 */
public class HelloQuartz implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloQuartz.class);

    private String name;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        JobDataMap map = detail.getJobDataMap(); //方法一：获得JobDataMap

        LOGGER.info("say hello to " + name + "[" + map.getInt("age") + "]" + " at "
                + new Date());
    }

    //方法二：属性的setter方法，会将JobDataMap的属性自动注入
    public void setName(String name) {
        this.name = name;
    }
}
