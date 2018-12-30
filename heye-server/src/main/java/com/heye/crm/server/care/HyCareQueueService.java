package com.heye.crm.server.care;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author : lishuming
 */
public class HyCareQueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyCareProducerTask.class);
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    /**
     * TODO: 如果队列堵塞，该怎么处理？
     */
    public void startProducer() {
        for (int i = 0; i < 1; i++) {
            HyCareProducerTask worker = new HyCareProducerTask("task-" + i);
            scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
        }
    }

    public void startConsumer() {
        for (int i = 0; i < 1; i++) {
            HyCareConsumerTask worker = new HyCareConsumerTask();
            scheduledThreadPool.scheduleAtFixedRate(worker, 0, 5, TimeUnit.SECONDS);
        }
    }

    public void awaitCompute() throws Exception {
        LOGGER.info("Shutting down executor...");

        scheduledThreadPool.shutdown();
        boolean isDone;
        do {
            isDone = scheduledThreadPool.awaitTermination(1, TimeUnit.DAYS);
            LOGGER.info("awaitTermination...");
        } while (!isDone);

        LOGGER.info("Finished all threads");
    }
}
