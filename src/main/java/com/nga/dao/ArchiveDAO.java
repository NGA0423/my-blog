package com.nga.dao;

import com.nga.model.ContentModel;

import java.util.List;

public class ArchiveDAO {
    private String date;
    private String count;
    private List<ContentModel> articles;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ContentModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ContentModel> articles) {
        this.articles = articles;
    }
}
