package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.CommentDAO;

import com.nga.dao.cond.ContentCond;
import com.nga.model.ContentModel;
import com.nga.util.StatisticsUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public interface ContentService {
    /**
     * 添加文章
     *
     * @param contentModel
     */
    public void addArticle(ContentModel contentModel);

    /**
     * 根据编号删除文章
     *
     * @param cid
     */
    public void deleteArticleById(Integer cid);

    /**
     * 更新文章
     *
     * @param contentModel
     */
    public void updateArticleById(ContentModel contentModel);

    /**
     * 更新分类
     *
     * @param ordinal
     * @param newCatefory
     */
    public void updateCategory(String ordinal, String newCatefory);

    /**
     * 根据编号获取文章
     *
     * @param cid
     * @return
     */
    public ContentModel getArticleById(Integer cid);

    /**
     * 根据条件获取文章列表
     * @param contentCond
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ContentModel> getArticlesByCond(ContentCond contentCond,int pageNum,int pageSize);

    /**
     * 添加文章点击量
     * @param contentModel
     */
    public void updateContentByCid(ContentModel contentModel);

    /**
     * 获取最近的文章
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ContentModel> getRecentlyArticle(int pageNum,int pageSize);

    /**
     * 搜索文章
     * @param para
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ContentModel> searchArticle(String para,int pageNum,int pageSize);
}
