package com.nga.service.impl;

import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.dao.ContentDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.mapper.ArticleMapper;
import com.nga.service.ArticleService;
import com.nga.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public PageInfo<ContentDAO> getArticlesByCond(ContentCond contentCond,int pageNum,int pageSize) {
        if (contentCond==null){
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        List<ContentDAO> contentDAOS = articleMapper.getArticlesByCond(contentCond);
        PageInfo<ContentDAO> pageInfo = new PageInfo<>(contentDAOS);
        System.out.println(pageInfo);
        return pageInfo;
    }
}
