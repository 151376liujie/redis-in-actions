package com.liujie.proj.chap01.cache;

import com.liujie.proj.chap01.model.City;
import com.liujie.proj.chap01.model.County;
import com.liujie.proj.chap01.model.Province;
import com.liujie.proj.utils.JedisUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 省市县三级关联
 * Created by liujie on 2016/7/23 23:29.
 */
public class ProvinceCao {

    private static final String H_PROVINCE_PREFIX = "h_key_province:";
    private static final String FIELD_PROVINCE_NAME = "name";
    private static final String FIELD_PROVINCE_RANK = "rank";
    private static final String Z_KEY_CITIES_OF_PROVINCE = "z_cities_of_province:";

    private static final String H_CITY_PREFIX = "h_key_city:";

    private static final String FIELD_CITY_NAME = "name";
    private static final String FIELD_CITY_RANK = "rank";
    private static final String Z_KEY_COUNTIES_OF_CITY = "z_counties_of_city:";

    private static final String H_COUNTY_PREFIX = "h_key_county:";
    private static final String FIELD_COUNTY_NAME = "name";
    private static final String FIELD_COUNTY_RANK = "rank";

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceCao.class);

    private String getHashKey(String key, int id) {
        return key + id;
    }

    /**
     * 从redis中查找所有省份
     *
     * @return
     */
    public List<Province> loadAllProvinces() {
        List<Province> list = new ArrayList<Province>();
        Set<String> keys = JedisUtils.keys(H_PROVINCE_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return list;
        }
        for (String provinceId : keys) {
            Map<String, String> map = JedisUtils.hgetAll(provinceId);
            Province province = new Province();
            try {
                BeanUtils.populate(province,map);
                int pId = Integer.valueOf(provinceId.split(":")[1]);
                province.setId(pId);
                list.add(province);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return list;
    }

    /**
     * 从redis中获取所有城市
     *
     * @return
     */
    public List<City> loadAllCities() {
        List<City> cities = new ArrayList<City>();
        Set<String> keys = JedisUtils.keys(H_CITY_PREFIX + "*");
        if (keys == null || keys.isEmpty()){
            return  cities;
        }

        for (String key : keys){
            Map<String, String> map = JedisUtils.hgetAll(key);
            City city = new City();
            try {
                BeanUtils.populate(city,map);
                city.setId(Integer.valueOf(key.split(":")[1]));
                cities.add(city);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return cities;
    }

    /**
     * 从redis中读出指定省份的所有城市列表
     *
     * @param provinceId
     * @return
     */
    public List<City> loadCitiesByProvinceId(int provinceId) {
        Set<String> cityIds = JedisUtils.zrange(getHashKey(Z_KEY_CITIES_OF_PROVINCE, provinceId), 0, -1);
        if (cityIds == null || cityIds.isEmpty()){
            return Collections.emptyList();
        }
        List<City> cities = new ArrayList<City>(cityIds.size());
        for (String cityId:cityIds){
            Map<String, String> map = JedisUtils.hgetAll(getHashKey(H_CITY_PREFIX, Integer.valueOf(cityId)));
            City city = new City();
            try {
                BeanUtils.populate(city,map);
                city.setId(Integer.valueOf(cityId));
                cities.add(city);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return cities;
    }

    /**
     * 从redis中读出指定城市下所有区县信息
     *
     * @param cityId
     * @return
     */
    public List<County> loadCountiesByCityId(int cityId) {
        Set<String> countyIds = JedisUtils.zrange(getHashKey(Z_KEY_COUNTIES_OF_CITY, cityId), 0, -1);
        if (countyIds == null || countyIds.isEmpty()){
            return Collections.emptyList();
        }
        List<County> counties = new ArrayList<County>(countyIds.size());
        for (String countyId:countyIds){
            Map<String, String> map = JedisUtils.hgetAll(getHashKey(H_COUNTY_PREFIX, Integer.valueOf(countyId)));
            County county = new County();
            try {
                BeanUtils.populate(county,map);
                county.setId(Integer.valueOf(countyId));
                counties.add(county);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
        return counties;
    }

    /**
     * 设置省份列表到redis
     *
     * @param provinces
     */
    public void setProvinces(List<Province> provinces) {
        if (provinces == null || provinces.isEmpty())
            return;
        for (Province province : provinces) {
            String hashKey = getHashKey(H_PROVINCE_PREFIX, province.getId());
            JedisUtils.hset(hashKey, FIELD_PROVINCE_NAME, province.getName());
            JedisUtils.hset(hashKey, FIELD_PROVINCE_RANK, String.valueOf(province.getRank()));
            List<City> cities = province.getCities();
            if (cities == null || cities.isEmpty()) {
                return;
            }
            setCities(cities);
            for (City city : cities) {
                JedisUtils.zadd(getHashKey(Z_KEY_CITIES_OF_PROVINCE, province.getId()), String.valueOf(city.getId()), city.getRank());
            }
        }
    }

    /**
     * 设置城市列表到redis
     *
     * @param cities
     */
    public void setCities(List<City> cities) {
        if (cities == null || cities.isEmpty()) {
            return;
        }
        for (City city : cities) {
            JedisUtils.hset(H_CITY_PREFIX + city.getId(), FIELD_CITY_NAME, city.getName());
            JedisUtils.hset(H_CITY_PREFIX + city.getId(), FIELD_CITY_RANK, String.valueOf(city.getRank()));
            List<County> counties = city.getCounties();
            if (counties == null || counties.isEmpty()) {
                return;
            }
            setCounties(counties);
            for (County county : counties) {
                JedisUtils.zadd(Z_KEY_COUNTIES_OF_CITY + city.getId(), String.valueOf(county.getId()), county.getRank());
            }
        }
    }

    /**
     * 设置区县到redis
     *
     * @param counties
     */
    public void setCounties(List<County> counties) {
        if (counties == null || counties.isEmpty())
            return;
        for (County county : counties) {
            JedisUtils.hset(H_COUNTY_PREFIX + county.getId(), FIELD_COUNTY_NAME, county.getName());
            JedisUtils.hset(H_COUNTY_PREFIX + county.getId(), FIELD_COUNTY_RANK, String.valueOf(county.getRank()));
        }
    }

}
