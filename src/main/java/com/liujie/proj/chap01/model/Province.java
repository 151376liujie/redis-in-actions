package com.liujie.proj.chap01.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujie on 2016/7/23 23:09.
 */
public class Province implements Serializable{

    private int id;
    private String name;
    private int rank;
    private List<City> cities = new ArrayList<City>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", cities=" + cities +
                '}';
    }
}
