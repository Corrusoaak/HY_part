package com.heye.crm.server.analyze;

import com.heye.crm.common.consts.Consts;
import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.common.utils.RedisClient;
import com.heye.crm.server.service.HyAnalyzeService;
import com.heye.crm.server.service.HyCustomerService;
import com.heye.crm.server.service.impl.HyAnalyzeServiceImpl;
import com.heye.crm.server.service.impl.HyCustomerServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : lishuming
 */
public class HyAnalyzeDailyQuartz implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAnalyzeDailyQuartz.class);

    private HyCustomerService hyCustomerService;
    private HyAnalyzeService hyAnalyzeService;

    private static Jedis jedis = null;

    static {
        try {
            jedis = new RedisClient().getJedis();
        } catch (Exception e) {
            LOGGER.warn("redis init failed: ", e);
        }
    }

    @Override
    public void execute(JobExecutionContext context) {
        // 从hy_customer表读取昨天的数据;
        // 按照性别、地域、年龄分类；
        // 写入hy_analyze表中；
        // 注意写入created_at时间为昨天的时间;

        LOGGER.info("start to daily quartz heye analyze...");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String thisDay = sdf.format(new Date());

            String lastAnaDay = jedis.get("analyze_daily_date");
            if (thisDay.equals(lastAnaDay)) {
                LOGGER.info("Already analyzed this day.");
                return;
            } else {
                LOGGER.info("Time to scan...");
                jedis.set("analyze_daily_date", thisDay);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        hyCustomerService = new HyCustomerServiceImpl();
        hyAnalyzeService = new HyAnalyzeServiceImpl();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date curDate = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date lastDate = calendar.getTime();
        Timestamp lastTime = new Timestamp(lastDate.getTime());
        Timestamp curTime = new Timestamp(curDate.getTime());

        // Newly Analyzes
        List<HyCustomer> newlycustomers = hyCustomerService.findCustomerByDate(lastTime, curTime);
        LOGGER.info("lastTime=" + lastTime + ", curTime=" + curTime);
        LOGGER.info("newlyCustomers list size=" + newlycustomers.size());
        List<HyAnalyze> newlyHyAnalyzes = HyAnalyzeUtil.getAnalyzeList(LOGGER, newlycustomers, lastTime,
                Consts.HY_ANALYZE_AGG_LEVEL_DAY, true);
        if (newlyHyAnalyzes.size() != 0) {
            hyAnalyzeService.saveList(newlyHyAnalyzes);
        } else {
            LOGGER.info("No newlyAnalyze saved");
        }

        // Sum Analyzes
        String farTimeStr = "1000-01-01 00:00:00";
        Timestamp farTime = Timestamp.valueOf(farTimeStr);

        List<HyCustomer> sumcustomers = hyCustomerService.findCustomerByDate(farTime, curTime);
        LOGGER.info("farTime=" + farTime + ", curTime=" + curTime);
        LOGGER.info("sumCustomers list size=" + sumcustomers.size());
        List<HyAnalyze> sumHyAnalyzes = HyAnalyzeUtil.getAnalyzeList(LOGGER, sumcustomers, lastTime,
                Consts.HY_ANALYZE_AGG_LEVEL_DAY, false);
        if (sumHyAnalyzes.size() != 0) {
            hyAnalyzeService.saveList(sumHyAnalyzes);
        } else {
            LOGGER.info("No sumAnalyze saved");
        }
    }
}
