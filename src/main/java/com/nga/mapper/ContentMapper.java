package com.nga.mapper;

import com.nga.dao.ArchiveDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.dao.ContentDAO;
import com.nga.model.ContentModel;
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
     * @param contentModel
     */
    public void addArticle(ContentModel contentModel);
    /**
     * 根据文章条件获取文章列表
     * @param contentCond
     * @return
     */
    public List<ContentModel> getArticlesByCond(ContentCond contentCond);
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
     * @param contentModel
     * @return
     */
    public int updateArticleById(ContentModel contentModel);

    /**
     * 根据编号获取文章
     * @param cid
     * @return
     */
    public ContentModel getArticleById(@Param("cid")Integer cid);

    /**
     * 获取归档数据
     * @param contentCond
     * @return
     */
    List<ArchiveDAO> getArchive(ContentCond contentCond);

    /**
     * 获取最近的文章(只包含id和title)
     * @return
     */
    List<ContentModel> getRecentlyArticle();

    /**
     * 搜索文章-根据标题或内容匹配
     * @param param
     * @return
     */
    List<ContentModel> searchArticle(@Param("param") String param);
}
