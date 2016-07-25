package com.liujie.proj.chap01.model;

import java.io.Serializable;

/**
 * Created by liujie on 2016/7/18 23:23.
 */
public class Article implements Serializable{


    private int id;
    private String title;
    private String content;
    private String postTime;
    private int vote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postTime='" + postTime + '\'' +
                ", vote=" + vote +
                '}';
    }
}
