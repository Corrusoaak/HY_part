package com.heye.crm.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lishuming
 */
public class RedisClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);

    //非切片额客户端连接
    private Jedis jedis;
    //非切片连接池
    private JedisPool jedisPool;
    //切片额客户端连接
    private ShardedJedis shardedJedis;
    //切片连接池
    private ShardedJedisPool shardedJedisPool;

    public RedisClient() {
        initialPool();
        jedis = jedisPool.getResource();

        /**
         initialShardedPool();
         shardedJedis = shardedJedisPool.getResource();*/
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedis;
    }

    public void setShardedJedis(ShardedJedis shardedJedis) {
        this.shardedJedis = shardedJedis;
    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(20);
        config.setMinIdle(5);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "47.106.80.130", 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);

        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("47.106.80.130", 6379, "master"));
        shards.add(new JedisShardInfo("47.106.80.130", 6378, "slave"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }
}
