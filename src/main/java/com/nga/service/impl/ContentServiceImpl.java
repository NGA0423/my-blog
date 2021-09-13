package com.nga.service.impl;

import com.nga.constan.ErrorConstant;
import com.nga.constan.WebConst;
import com.nga.dao.CommentDAO;
import com.nga.dao.RelationshipDAO;
import com.nga.dao.cond.CommentCond;
import com.nga.dao.cond.ContentCond;
import com.nga.dao.ContentDAO;
import com.nga.mapper.*;
import com.nga.service.ContentService;
import com.nga.service.MetaService;
import com.nga.util.BusinessException;
import com.nga.util.StatisticsUtil;
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


    /**
     * 添加文章
     *
     * @param contentDAO
     */
    @Transactional
    @Override
    @CacheEvict(value = {"articleCache","articleCaches"},allEntries = true,beforeInvocation = true)
    public void addArticle(ContentDAO contentDAO) {
        if (contentDAO==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        if (StringUtils.isBlank(contentDAO.getTitle()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_CAN_NOT_EMPTY);
        if (contentDAO.getTitle().length()> WebConst.MAX_TITLE_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.TITLE_IS_TOO_LONG);
        if (StringUtils.isBlank(contentDAO.getContent()))
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_CAN_NOT_EMPTY);
        if (contentDAO.getContent().length()>WebConst.MAX_TEXT_COUNT)
            throw BusinessException.withErrorCode(ErrorConstant.Article.CONTENT_IS_TOO_LONG);
        // 标签和分类
        String tags = contentDAO.getTags();
        String categories = contentDAO.getCategories();
        contentMapper.addArticle(contentDAO);
        Integer cid = contentDAO.getCid();
        metaService.addMetas(cid,tags,TypesUtil.TAG.getType());
        metaService.addMetas(cid,categories,TypesUtil.CATEGORY.getType());
    }

    /**
     * 获取评论列表
     *
     * @param limit
     * @return
     */
    @Override
    public List<CommentDAO> getComments(int limit) {
        if (limit < 0 || limit > 10)
            limit = 10;
        List<CommentDAO> commentByCond = commentMapper.getCommentByCond(new CommentCond());
        return commentByCond;
    }

    /**
     * 获取最新文章
     *
     * @param limit
     * @return
     */
    @Override
    public List<ContentDAO> getNewArticles(int limit) {
        if (limit < 0 || limit > 10)
            limit = 10;
        List<ContentDAO> articlesByCond = contentMapper.getArticlesByCond(new ContentCond());
        return articlesByCond;
    }

    @Override
    public StatisticsUtil getArticleCount() {
        //文章总数
        Long articleCount = contentMapper.getArticleCount();
        // 评论总数
        Long commentsCount = commentMapper.getCommentsCount();
        //
        Long links = metaMapper.getMetasCountByType(TypesUtil.LINK.getType());
        Long atts = attAchMapper.getAttsCount();
        StatisticsUtil statistics = new StatisticsUtil();
        statistics.setArticles(articleCount);
        statistics.setComments(commentsCount);
        statistics.setLinks(links);
        statistics.setAttachs(atts);
        return statistics;
    }

    /**
     * 根据编号删除文章
     *
     * @param cid
     */
    @Override
    public void deleteArticleById(Integer cid) {
        if (cid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        contentMapper.deleteArticleById(cid);
        // 同时也要删除该文章的所有评论
        List<CommentDAO> comments = commentMapper.getCommentsByCId(cid);
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

    /**
     * 更新分类
     *
     * @param ordinal
     * @param newCatefory
     */
    @Override
    @Transactional
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public void updateCategory(String ordinal, String newCatefory) {
        ContentCond cond = new ContentCond();
        cond.setCategory(ordinal);
        List<ContentDAO> articlesByCond = contentMapper.getArticlesByCond(cond);
        articlesByCond.forEach(article->{
            article.setCategories(article.getCategories().replace(ordinal,newCatefory));
            contentMapper.updateArticleById(article);
        });
    }

    /**
     * 根据编号获取文章
     *
     * @param cid
     * @return
     */
    @Override
    @CacheEvict(value = {"articleCache", "articleCaches"}, allEntries = true, beforeInvocation = true)
    public ContentDAO getArticleById(Integer cid) {
        if (cid==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return contentMapper.getArticleById(cid);
    }

    /**
     * 更新文章
     *
     * @param contentDAO
     */
    @Override
    public void updateArticleById(ContentDAO contentDAO) {
        // 分类和标签
        String tags = contentDAO.getTags();
        String categories = contentDAO.getCategories();

        contentMapper.updateArticleById(contentDAO);
        Integer cid = contentDAO.getCid();
        relationshipMapper.deleteRelationShipByCId(cid);
        metaService.addMetas(cid,tags,TypesUtil.TAG.getType());
        metaService.addMetas(cid,categories,TypesUtil.CATEGORY.getType());
    }

}
