package com.liujie.proj.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * JedisUtils Tester.
 *
 * @author 刘杰
 * @version 1.0
 * @since <pre>07/24/2016</pre>
 */
public class JedisUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtilsTest.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
//        JedisUtils.flushDb();
    }

    /**
     * Method: set(String key, String value)
     */
    @Test
    public void testSet() throws Exception {
        JedisUtils.set("hello", "world");
        String hello = JedisUtils.get("hello");
        Assert.assertEquals(hello,"world");
    }

    /**
     * Method: get(String key)
     */
    @Test
    public void testGet() throws Exception {
        JedisUtils.set("hello", "world");
        String hello = JedisUtils.get("hello");
        Assert.assertEquals(hello,"world");
    }

    /**
     * Method: flushDb()
     */
    @Test
    public void testFlushDb() throws Exception {
        JedisUtils.flushDb();
        Set<String> keys = JedisUtils.keys("*");
        Assert.assertEquals(keys.size(), 0);
    }

    /**
     * Method: hget(String key, String field)
     */
    @Test
    public void testHget() throws Exception {
        JedisUtils.hset("hkey", "hfield", "hvalue");
        JedisUtils.hset("hkey", "hfield2", "hvalue2");
        String value = JedisUtils.hget("hkey", "hfield");
        String value2 = JedisUtils.hget("hkey", "hfield2");

        Assert.assertEquals(value, "hvalue");
        Assert.assertEquals(value2, "hvalue2");

    }

    /**
     * Method: hgetAll(String key)
     */
    @Test
    public void testHgetAll() throws Exception {
        JedisUtils.hset("hkey", "hfield", "hvalue");
        JedisUtils.hset("hkey", "hfield2", "hvalue2");
        String value = JedisUtils.hget("hkey", "hfield");
        String value2 = JedisUtils.hget("hkey", "hfield2");
        Map<String, String> map = JedisUtils.hgetAll("hkey");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            LOGGER.info("key:{},value:{}", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Method: hset(String key, String field, String value)
     */
    @Test
    public void testHset() throws Exception {
        JedisUtils.hset("hkey", "hfield", "hvalue");
        JedisUtils.hset("hkey", "hfield2", "hvalue2");
        String value = JedisUtils.hget("hkey", "hfield");
        String value2 = JedisUtils.hget("hkey", "hfield2");

        Assert.assertEquals(value, "hvalue");
        Assert.assertEquals(value2, "hvalue2");
    }

    /**
     * Method: lpush(String key, String[] elements)
     */
    @Test
    public void testLpush() throws Exception {
        JedisUtils.lpush("list",new String[]{"a","b","c"});
    }

    /**
     * Method: lrange(String key, int start, int end)
     */
    @Test
    public void testLrange() throws Exception {
        //TODO: Test goes here...
    }


}
