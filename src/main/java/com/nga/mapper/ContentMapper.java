package com.nga.mapper;

import com.nga.dao.cond.ContentCond;
import com.nga.dao.ContentDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContentMapper {
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
}
