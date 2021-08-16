package com.nga.service.impl;

import com.nga.dao.CommentDAO;
import com.nga.dao.cond.CommentCond;
import com.nga.dao.cond.ContentCond;
import com.nga.dao.ContentDAO;
import com.nga.mapper.AttAchMapper;
import com.nga.mapper.CommentMapper;
import com.nga.mapper.ContentMapper;
import com.nga.mapper.MetaMapper;
import com.nga.service.ContentService;
import com.nga.util.StatisticsUtil;
import com.nga.util.TypesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
