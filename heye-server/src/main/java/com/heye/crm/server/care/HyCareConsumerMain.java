package com.heye.crm.server.care;

/**
 * @author : lishuming
 */
public class HyCareConsumerMain {
    public static void main(String[] args) {
        HyCareQueueService hyCareQueueService = new HyCareQueueService();
        hyCareQueueService.startConsumer();
    }
}
