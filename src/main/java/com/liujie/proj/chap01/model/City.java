package com.liujie.proj.chap01.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujie on 2016/7/23 23:20.
 */
public class City implements Serializable{

    private int id;
    private String name;

    private int rank;

    private List<County> counties = new ArrayList<County>();

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

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", counties=" + counties +
                '}';
    }
}
