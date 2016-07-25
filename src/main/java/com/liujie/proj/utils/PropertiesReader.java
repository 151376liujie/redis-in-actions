package com.liujie.proj.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by liujie on 2016/7/18 22:57.
 */
public class PropertiesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream("redis.properties");
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static String getString(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        String property = PROPERTIES.getProperty(key);
        if (StringUtils.isAlphanumeric(property)) {
            return Integer.valueOf(property);
        }
        return 0;
    }

    public static boolean getBoolean(String key) {
        String value = PROPERTIES.getProperty(key);
        return BooleanUtils.toBoolean(value);
    }
}
