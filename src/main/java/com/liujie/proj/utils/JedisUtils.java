package com.liujie.proj.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis连接工具
 * Created by liujie on 2016/7/19 23:19.
 */
public class JedisUtils {

    private static JedisPool pool;

    static {
        pool = getPool();
    }

    private  static JedisPool getPool() {
        String host = PropertiesReader.getString("redis.host", "localhost");
        int port = PropertiesReader.getInt("redis.port");
        int minIdle = PropertiesReader.getInt("redis.minIdle");
        boolean testOnBorrow = PropertiesReader.getBoolean("redis.testOnBorrow");
        int maxTotal = PropertiesReader.getInt("redis.maxTotal");
        int maxWaitMillis = PropertiesReader.getInt("redis.maxWaitMillis");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        pool = new JedisPool(config,host, port);
        return pool;
    }

    /**
     * 往redis中设置key与value
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = pool.getResource();
        jedis.set(key, value);
        jedis.close();
    }

    /**
     * 从redis中读取指定key对应的value值
     * @param key
     * @return
     */
    public static String get(String key){
        Jedis jedis = pool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 清空redis数据库
     */
    public static void flushDb(){
        Jedis resource = pool.getResource();
        resource.flushDB();
        resource.close();
    }

    /**
     * 获取指定key和field的值
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key,String field){
        Jedis jedis = pool.getResource();
        String value = jedis.hget(key, field);
        jedis.close();
        return value;
    }

    /**
     * 获取指定key的所有hashMap
     * @param key
     * @return
     */
    public static Map<String,String> hgetAll(String key){
        Jedis jedis = pool.getResource();
        Map<String, String> map = jedis.hgetAll(key);
        jedis.close();
        return map;
    }

    /**
     * 设置指定key,field,value
     * @param key
     * @param field
     * @param value
     */
    public static void hset(String key,String field,String value){
        Jedis jedis = pool.getResource();
        jedis.hset(key,field,value);
        jedis.close();
    }


    /**
     * 将key和众多元素推入redis链表中
     * @param key
     * @param elements
     */
    public static void lpush(String key,String[] elements){
        Jedis jedis = pool.getResource();
        jedis.lpush(key,elements);
        jedis.close();
    }

    /**
     * 从redis中获取指定key，指定长度的链表元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(String key,int start,int end){
        Jedis jedis = pool.getResource();
        List<String> list = jedis.lrange(key, start, end);
        jedis.close();
        return list;
    }

    /**
     * 获取redis中所有key
     * @return
     */
    public static Set<String> keys(String pattern){
        Jedis jedis = pool.getResource();
        Set<String> keys = jedis.keys(pattern);
        jedis.close();
        return keys;
    }

    /**
     * 将指定key和value及score放到redis
     * @param key
     * @param member
     * @param score
     */
    public static void zadd(String key,String member,double score){
        Jedis jedis = pool.getResource();
        jedis.zadd(key, score, member);
        jedis.close();
    }

    /**
     * 将指定key和value和score的映射集合放入redis
     * @param key
     * @param scoreMap
     */
    public static void zadd(String key,Map<String,Double> scoreMap){
        Jedis jedis = pool.getResource();
        jedis.zadd(key,scoreMap);
        jedis.close();
    }

    public static Set<String> zrange(String key, int start, int end){
        Jedis jedis = pool.getResource();
        Set<String> zrange = jedis.zrange(key, start, end);
        if (zrange == null ||zrange.isEmpty()){
            return Collections.emptySet();
        }
        jedis.close();
        return zrange;
    }

}
