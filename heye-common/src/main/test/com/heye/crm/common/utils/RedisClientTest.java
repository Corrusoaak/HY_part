package com.heye.crm.common.utils;

import org.junit.Test;

/**
 * @author : lishuming
 */
public class RedisClientTest {
    @Test
    public void testRedisClient() {
        RedisClient r = new RedisClient();

        r.getJedis().set("k", "v");

        assert r.getJedis().get("k").equalsIgnoreCase("v");
    }
}
