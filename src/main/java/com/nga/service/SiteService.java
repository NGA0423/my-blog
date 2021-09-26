package com.nga.service;

import com.nga.dao.ArchiveDAO;
import com.nga.dao.MetaDAO;
import com.nga.dao.StatisticsDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.model.CommentModel;
import com.nga.model.ContentModel;

import java.util.List;

public interface SiteService {
    /**
     * 获取评论列表
     * @param limit
     * @return
     */
    public List<CommentModel> getComments(int limit);

    /**
     * 获取最新文章
     * @param limit
     * @return
     */
    public List<ContentModel> getNewArticles(int limit);

    /**
     * 获取单条评论
     * @param coid
     * @return
     */
    public CommentModel getComment(Integer coid);

    /**
     * 获取后台统计数据
     * @return
     */
    public StatisticsDAO getStatistics();

    /**
     * 获取归档列表-只是获取日期和数量
     * @param contentCond
     * @return
     */
    public List<ArchiveDAO> getArchivesSimple(ContentCond contentCond);

    /**
     * 获取归档列表
     * @param contentCond
     * @return
     */
    public List<ArchiveDAO> getArchives(ContentCond contentCond);

    /**
     * 获取分类/标签列表
     * @param type
     * @param orderBy
     * @param limit
     * @return
     */
    public List<MetaDAO> getMetas(String type,String orderBy,int limit);
}
