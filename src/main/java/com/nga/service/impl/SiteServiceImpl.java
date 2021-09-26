package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.nga.constan.ErrorConstant;
import com.nga.constan.WebConst;
import com.nga.dao.ArchiveDAO;
import com.nga.dao.ContentDAO;
import com.nga.dao.MetaDAO;
import com.nga.dao.StatisticsDAO;
import com.nga.dao.cond.CommentCond;
import com.nga.dao.cond.ContentCond;
import com.nga.mapper.AttAchMapper;
import com.nga.mapper.CommentMapper;
import com.nga.mapper.ContentMapper;
import com.nga.mapper.MetaMapper;
import com.nga.model.CommentModel;
import com.nga.model.ContentModel;
import com.nga.service.SiteService;
import com.nga.util.BusinessException;
import com.nga.util.DateKitUtil;
import com.nga.util.TypesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private MetaMapper metaMapper;

    @Autowired
    private AttAchMapper attAchMapper;

    @Override
    @Cacheable(value = "siteCache", key = "'comments_'+#p0")
    public List<CommentModel> getComments(int limit) {
        LOGGER.debug("Enter recentComments method:limit={}", limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(1, limit);
        List<CommentModel> rs = commentMapper.getCommentsByCond(new CommentCond());
        LOGGER.debug("Exit recentComments method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'newArticles_'+#p0")
    public List<ContentModel> getNewArticles(int limit) {
        LOGGER.debug("Enter recentArticles method:limit={}", limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        PageHelper.startPage(1, limit);
        List<ContentModel> rs = contentMapper.getArticlesByCond(new ContentCond());
        LOGGER.debug("Exit recentArticles method");
        return rs;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'comment_'+#p0")
    public CommentModel getComment(Integer coid) {
        LOGGER.debug("Enter recentComment method", coid);
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        CommentModel comment = commentMapper.getCommentById(coid);
        LOGGER.debug("Exit recentComment method");
        return comment;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'statistics_'")
    public StatisticsDAO getStatistics() {
        LOGGER.debug("Enter recentStatistics method");
        // 文章数
        Long articles = contentMapper.getArticleCount();
        Long comments = commentMapper.getCommentsCount();
        Long links = metaMapper.getMetasCountByType(TypesUtil.LINK.getType());
        Long atts = attAchMapper.getAttsCount();

        StatisticsDAO statisticsDAO = new StatisticsDAO();
        statisticsDAO.setArticles(articles);
        statisticsDAO.setAttachs(atts);
        statisticsDAO.setComments(comments);
        statisticsDAO.setLinks(links);

        LOGGER.debug("Exit recentStatistics method");
        return statisticsDAO;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archivesSimple_'+#p0")
    public List<ArchiveDAO> getArchivesSimple(ContentCond contentCond) {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveDAO> archive = contentMapper.getArchive(contentCond);
        LOGGER.debug("Exit getArchives method");
        return archive;
    }

    @Override
    @Cacheable(value = "siteCache", key = "'archives_'+#p0")
    public List<ArchiveDAO> getArchives(ContentCond contentCond) {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveDAO> archive = contentMapper.getArchive(contentCond);
        paresArchives(archive, contentCond);
        LOGGER.debug("Exit getArchives method");
        return archive;
    }

    private void paresArchives(List<ArchiveDAO> archives, ContentCond contentCond) {
        if (archives != null) {
            archives.forEach(archive -> {
                String date = archive.getDate();
                Date sd = DateKitUtil.dateFormat(date, "yyyy年MM月");
                int start = DateKitUtil.getUnixTimeByDate(sd);
                int end = DateKitUtil.getUnixTimeByDate(DateKitUtil.dateAdd(DateKitUtil.INTERVAL_MONTH, sd, 1)) - 1;
                ContentCond cond = new ContentCond();
                cond.setStartTime(start);
                cond.setEndTime(end);
                cond.setType(contentCond.getType());
                List<ContentModel> contents = contentMapper.getArticlesByCond(cond);
                archive.setArticles(contents);
            });
        }
    }

    @Override
    @Cacheable(value = "siteCache", key = "'metas_'+#p0")
    public List<MetaDAO> getMetas(String type, String orderBy, int limit) {
        LOGGER.debug("Enter metas method");
        List<MetaDAO> retList = null;
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(orderBy)) {
                orderBy = "count desc,a.mid desc";
            }
            if (limit < 1 || limit > WebConst.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("limit", limit);
            retList = metaMapper.selectFromSql(paraMap);
        }
        return retList;
    }
}
