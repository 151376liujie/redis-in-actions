package com.liujie.proj.utils;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liujie on 2016/7/18 23:36.
 */
public class PropertiesReaderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    @Test
    public void getString() throws Exception {
        String host = PropertiesReader.getString("redis.host","default host");
        String port = PropertiesReader.getString("redis.port","default port");
        LOGGER.info(host);
        LOGGER.info(port);
    }

    @Test
    public void getInt() throws Exception {
        int port = PropertiesReader.getInt("redis.port");
        LOGGER.info(port+"");
    }

    @Test
    public void getBoolean(){
        boolean aBoolean = PropertiesReader.getBoolean("xxx.yyy");
        LOGGER.info(""+aBoolean);
        Assert.assertFalse(aBoolean);
    }


}