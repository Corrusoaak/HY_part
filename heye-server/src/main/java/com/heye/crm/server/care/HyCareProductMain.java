package com.heye.crm.server.care;

/**
 * @author : lishuming
 */
public class HyCareProductMain {
    public static void main(String[] args) {
        HyCareQueueService hyCareQueueService = new HyCareQueueService();
        hyCareQueueService.startProducer();
    }
}
