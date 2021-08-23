package com.nga.mapper;

import com.nga.dao.ContentDAO;
import com.nga.dao.cond.ContentCond;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper {
    /**
     * 根据条件获取文章列表
     * @param contentCond
     * @return
     */
    public List<ContentDAO> getArticlesByCond(ContentCond contentCond);
}
