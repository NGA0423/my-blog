package com.nga.mapper;

import com.nga.dao.cond.ContentCond;
import com.nga.dao.ContentDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContentMapper {
    /**
     * 添加文章
     *
     * @param contentDAO
     */
    public void addArticle(ContentDAO contentDAO);
    /**
     * 根据文章条件获取文章列表
     * @param contentCond
     * @return
     */
    public List<ContentDAO> getArticlesByCond(ContentCond contentCond);
    /**
     * 获取文章数量
     * @return
     */
    public Long getArticleCount();

    /**
     * 根据编号删除文章
     * @param cid
     */
    public int deleteArticleById(@Param("cid") Integer cid);

    /**
     * 更新文章
     * @param contentDAO
     * @return
     */
    public int updateArticleById(ContentDAO contentDAO);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    public ContentDAO getArticleById(@Param("cid")Integer cid);
}
