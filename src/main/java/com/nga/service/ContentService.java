package com.nga.service;

import com.nga.dao.CommentDAO;
import com.nga.dao.ContentDAO;
import com.nga.util.StatisticsUtil;

import java.util.List;

public interface ContentService {
    /**
     * 获取评论列表
     * @param limit
     * @return
     */
    public List<CommentDAO> getComments(int limit);
    /**
     * 获取最新文章
     * @param limit
     * @return
     */
    public List<ContentDAO> getNewArticles(int limit);
    /**
     * 查询文章总数
     * @return
     */
    public StatisticsUtil getArticleCount();

}
