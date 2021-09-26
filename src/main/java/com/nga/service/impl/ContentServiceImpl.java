package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.constan.WebConst;
import com.nga.dao.RelationshipDAO;
import com.nga.dao.cond.ContentCond;
import com.nga.mapper.*;
import com.nga.model.CommentModel;
import com.nga.model.ContentModel;
import com.nga.service.ContentService;
import com.nga.service.MetaService;
import com.nga.util.BusinessException;
import com.nga.util.TypesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private AttAchMapper attAchMapper;
    @Autowired
    private RelationshipMapper relationshipMapper;
    @Autowired
    private MetaService metaService;

    @Transactional
    @Override
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void addArticle(ContentModel contentModel) {
        if (contentModel == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentModel.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentModel.getTitle().length() > WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentModel.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentModel.getContent().length() > WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);
        // 标签和分类
        String tags = contentModel.getTags();
        String categories = contentModel.getCategories();
        contentMapper.addArticle(contentModel);
        Integer cid = contentModel.getCid();
        metaService.addMetas(cid, tags, TypesUtil.TAG.getType());
        metaService.addMetas(cid, categories, TypesUtil.CATEGORY.getType());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void deleteArticleById(Integer cid) {
        if (cid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentMapper.deleteArticleById(cid);
        // 同时也要删除该文章的所有评论
        List<CommentModel> comments = commentMapper.getCommentsByCId(cid);
        if (comments != null && comments.size() > 0) {
            comments.forEach(comment -> {
                commentMapper.deleteComment(comment.getCoid());
            });
        }
        // 删除标签和分类关联
        List<RelationshipDAO> relationShipByCid = relationshipMapper.getRelationShipByCid(cid);
        if (relationShipByCid != null && relationShipByCid.size() > 0) {
            relationshipMapper.deleteRelationShipByCId(cid);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateArticleById(ContentModel contentModel) {
        // 分类和标签
        String tags = contentModel.getTags();
        String categories = contentModel.getCategories();

        contentMapper.updateArticleById(contentModel);
        Integer cid = contentModel.getCid();
        relationshipMapper.deleteRelationShipByCId(cid);
        metaService.addMetas(cid, tags, TypesUtil.TAG.getType());
        metaService.addMetas(cid, categories, TypesUtil.CATEGORY.getType());
    }

    @Override
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCategory(String ordinal, String newCatefory) {
        ContentCond cond = new ContentCond();
        cond.setCategory(ordinal);
        List<ContentModel> articlesByCond = contentMapper.getArticlesByCond(cond);
        articlesByCond.forEach(article -> {
            article.setCategories(article.getCategories().replace(ordinal, newCatefory));
            contentMapper.updateArticleById(article);
        });
    }

    @Override
    @Cacheable(value = "articleCache", key = "'articleById_'+#p0")
    public ContentModel getArticleById(Integer cid) {
        if (cid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentMapper.getArticleById(cid);
    }

    @Override
    @Cacheable(value = "articleCache", key = "'articleById_'+#p1+'type_'+#p0.type")
    public PageInfo<ContentModel> getArticlesByCond(ContentCond contentCond, int pageNum, int pageSize) {
        if (contentCond == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum, pageSize);
        List<ContentModel> contents = contentMapper.getArticlesByCond(contentCond);
        PageInfo<ContentModel> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "articleCache", key = "'recentlyArticle_'+#p0")
    public void updateContentByCid(ContentModel contentModel) {
        if (contentModel != null && contentModel.getCid() != null){
            contentMapper.updateArticleById(contentModel);
        }
    }

    @Override
    @Cacheable(value = "articleCache", key = "'recentlyArticle_'+#p0")
    public PageInfo<ContentModel> getRecentlyArticle(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentModel> contents = contentMapper.getRecentlyArticle();
        PageInfo<ContentModel> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    public PageInfo<ContentModel> searchArticle(String param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ContentModel> contentModels = contentMapper.searchArticle(param);
        PageInfo<ContentModel> pageInfo = new PageInfo<>(contentModels);
        return pageInfo;
    }
}
