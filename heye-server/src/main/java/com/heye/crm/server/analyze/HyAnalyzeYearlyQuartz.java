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
import java.util.Date;
import java.util.List;

/**
 * @author lishuming
 */
public class HyAnalyzeYearlyQuartz implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAnalyzeYearlyQuartz.class);

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
        LOGGER.info("start to yearly quartz heye analyze...");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String lastYear = String.valueOf(Integer.valueOf(sdf.format(new Date())) - 1); // we want to analyze last year

        String lastAnaYear = jedis.get("analyze_yearly_date");
        if (lastYear.equals(lastAnaYear)) {
            LOGGER.info("Already analyzed (data of last year).");
            return;
        }
        LOGGER.info("Time to scan...");
        jedis.set("analyze_yearly_date", lastYear);

        HyCustomerService hyCustomerService = new HyCustomerServiceImpl();
        HyAnalyzeService hyAnalyzeService = new HyAnalyzeServiceImpl();

        Timestamp lastTime = Timestamp.valueOf(lastYear + "-01-01 00:00:00");
        Timestamp curTime = Timestamp.valueOf(lastYear + "-12-31 23:59:59");

        // Newly Analyzes
        List<HyCustomer> newlycustomers = hyCustomerService.findCustomerByDate(lastTime, curTime);
        LOGGER.info("lastTime=" + lastTime + ", curTime=" + curTime);
        LOGGER.info("newlyCustomers list size=" + newlycustomers.size());
        List<HyAnalyze> newlyHyAnalyzes = HyAnalyzeUtil.getAnalyzeList(LOGGER, newlycustomers, lastTime,
                Consts.HY_ANALYZE_AGG_LEVEL_YEAR, true);
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
                Consts.HY_ANALYZE_AGG_LEVEL_YEAR, false);
        if (sumHyAnalyzes.size() != 0) {
            hyAnalyzeService.saveList(sumHyAnalyzes);
        } else {
            LOGGER.info("No sumAnalyze saved");
        }
    }
}
