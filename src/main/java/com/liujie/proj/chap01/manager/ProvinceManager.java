package com.liujie.proj.chap01.manager;

import com.liujie.proj.chap01.cache.ProvinceCao;
import com.liujie.proj.chap01.model.City;
import com.liujie.proj.chap01.model.County;
import com.liujie.proj.chap01.model.Province;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liujie on 2016/7/23 23:28.
 */
public class ProvinceManager {

    private static ProvinceCao provinceCao = new ProvinceCao();

    public static void main(String[] args) {

        ProvinceManager manager = new ProvinceManager();
        List<County> counties = provinceCao.loadCountiesByCityId(10001);
        for (County city : counties ) {
            System.out.println(city);
        }
    }

    public static void setCounties() {

        Province province = new Province();
        province.setId(1000);
        province.setName("河南省");
        province.setRank(1);

        List<City> cities = new ArrayList<>();
        City city_xinyang = new City();
        city_xinyang.setId(10001);
        city_xinyang.setName("信阳市");
        city_xinyang.setRank(2);
        cities.add(city_xinyang);

        City city_zhengzhou = new City();
        city_zhengzhou.setId(10002);
        city_zhengzhou.setName("郑州市");
        city_zhengzhou.setRank(1);
        cities.add(city_zhengzhou);


        List<County> counties = new ArrayList<>();
        County county = new County();
        county.setId(100);
        county.setName("平桥区");
        county.setRank(1);
        counties.add(county);

        County county2 = new County();
        county2.setId(101);
        county2.setName("浉河区");
        county2.setRank(2);
        counties.add(county2);

        County county3 = new County();
        county3.setId(102);
        county3.setName("城南区");
        county3.setRank(3);
        counties.add(county3);

        city_xinyang.setCounties(counties);

        List<County> counties2 = new ArrayList<>();
        County county4 = new County();
        county4.setId(200);
        county4.setName("二七区");
        county4.setRank(3);
        counties2.add(county4);

        County county5 = new County();
        county5.setId(201);
        county5.setName("金水区");
        county5.setRank(1);
        counties2.add(county5);

        city_zhengzhou.setCounties(counties2);

        province.setCities(cities);
        System.out.println(province);
        provinceCao.setProvinces(Arrays.asList(province));

    }

    public void loadAllProvince() {
        ProvinceCao provinceCao = new ProvinceCao();
        List<Province> list = provinceCao.loadAllProvinces();
        for (Province province : list) {
            System.out.println(province);
        }
    }

}
