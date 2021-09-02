package com.nga.service;

import com.nga.dao.CommentDAO;
import com.nga.dao.ContentDAO;
import com.nga.util.StatisticsUtil;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 根据编号删除文章
     * @param cid
     */
    public void deleteArticleById(Integer cid);

    /**
     * 更新分类
     * @param ordinal
     * @param newCatefory
     */
    public void updateCategory(String ordinal,String newCatefory);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    public ContentDAO getArticleById(Integer cid);

    /**
     * 更新文章
     * @param contentDAO
     */
    public void updateArticleById(ContentDAO contentDAO);

}
