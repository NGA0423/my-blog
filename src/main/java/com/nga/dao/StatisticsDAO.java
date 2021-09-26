package com.nga.dao;

/**
 * 后台统计对象
 */
public class StatisticsDAO {
    /**
     * 文章数
     */
    private Long articles;
    /**
     * 文章数
     */
    private Long comments;
    /**
     * 文章数
     */
    private Long links;
    /**
     * 文章数
     */
    private Long attachs;

    public Long getArticles() {
        return articles;
    }

    public void setArticles(Long articles) {
        this.articles = articles;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLinks() {
        return links;
    }

    public void setLinks(Long links) {
        this.links = links;
    }

    public Long getAttachs() {
        return attachs;
    }

    public void setAttachs(Long attachs) {
        this.attachs = attachs;
    }
}
