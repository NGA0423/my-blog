package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.dao.ContentDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.mapper.ArticleMapper;
import com.nga.mapper.ContentMapper;
import com.nga.service.ArticleService;
import com.nga.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Override
    public PageInfo<ContentDAO> getArticlesByCond(ContentCond contentCond,int pageNum,int pageSize) {
        if (contentCond==null){
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<ContentDAO> contentDAOS = contentMapper.getArticlesByCond(contentCond);
        PageInfo<ContentDAO> pageInfo = new PageInfo<>(contentDAOS);
        return pageInfo;
    }
}
