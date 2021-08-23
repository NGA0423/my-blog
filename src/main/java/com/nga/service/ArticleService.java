package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.ContentDAO;
import com.nga.dao.cond.ContentCond;

public interface ArticleService {
    /**
     * 根据条件获取文章列表
     * @return
     */
    public PageInfo<ContentDAO> getArticlesByCond(ContentCond contentCond,int pageNum,int pageSize);
}
