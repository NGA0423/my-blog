package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.dao.cond.CommentCond;
import com.nga.mapper.CommentMapper;
import com.nga.model.CommentModel;
import com.nga.model.ContentModel;
import com.nga.service.CommentsService;
import com.nga.service.ContentService;
import com.nga.util.BusinessException;
import com.nga.util.DateKitUtil;
import com.nga.util.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 评论实现类
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ContentService contentService;

    private static final Map<String, String> STATUS_MAP = new ConcurrentHashMap<>();
    /**
     * 评论状态：正常
     */
    private static final String STATUS_NORMAL = "approved";

    /**
     * 评论状态：不显示
     */
    private static final String STATus_BLANK = "not_audit";

    static {
        STATUS_MAP.put("approved", STATUS_NORMAL);
        STATUS_MAP.put("not_audit", STATus_BLANK);
    }

    /**
     * 新增评论
     *
     * @param commentModel
     */
    @Override
    @Transactional
    @CacheEvict(value = "commentCache", allEntries = true)
    public void addComment(CommentModel commentModel) {
        String msg = null;
        if (commentModel == null) {
            msg = "评论对象为空";
        }
        if (commentModel != null) {
            if (StringUtils.isBlank(commentModel.getAuthor())) {
                commentModel.setAuthor("热心网友");
            }
            if (StringUtils.isBlank(commentModel.getMail()) && TaleUtils.isEmail(commentModel.getMail())) {
                msg = "请正确输入邮箱格式";
            }
            if (StringUtils.isBlank(commentModel.getContent())) {
                msg = "评论不能为空";
            }
            if (commentModel.getContent().length() < 5 || commentModel.getContent().length() > 2000) {
                msg = "评论字数在5-2000字符";
            }
            if (commentModel.getCid() == null) {
                msg = "评论文章不能为空";
            }
            if (msg != null)
                throw BusinessException.withErrorCode(msg);
            ContentModel articleById = contentService.getArticleById(commentModel.getCid());
            if (articleById == null)
                throw BusinessException.withErrorCode("该文章不存在");
            commentModel.setOwnerId(articleById.getAuthorId());
            commentModel.setStatus(STATUS_MAP.get(STATus_BLANK));
            commentModel.setCreated(DateKitUtil.getCurrentUnixTime());
            commentMapper.addComment(commentModel);

            ContentModel temp = new ContentModel();
            temp.setCid(articleById.getCid());
            Integer count = articleById.getCommentsNum();
            if (count == null) {
                count = 0;
            }
            temp.setCommentsNum(count + 1);
            contentService.updateContentByCid(temp);
        }
    }

    /**
     * 删除评论
     *
     * @param coid
     */
    @Transactional
    @Override
    @CacheEvict(value = "commentCache", allEntries = true)
    public void deleteComment(Integer coid) {
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        // 如果存在子评论一并删除
        // 查找当前评论是否有子评论
        CommentCond commentCond = new CommentCond();
        commentCond.setParent(coid);
        CommentModel commentById = commentMapper.getCommentById(coid);
        List<CommentModel> childComments = commentMapper.getCommentByCond(commentCond);
        Integer count = 0;
        // 删除子评论
        if (childComments != null && childComments.size() > 0) {
            for (int i = 0; i < childComments.size(); i++) {
                commentMapper.deleteComment(childComments.get(i).getCoid());
                count++;
            }
        }
        // 删除当前评论
        commentMapper.deleteComment(coid);
        count++;

        // 更新当前文章的评论数
        ContentModel contentModel = contentService.getArticleById(commentById.getCid());
        if (contentModel != null && contentModel.getCommentsNum() != null && contentModel.getCommentsNum() != 0) {
            contentModel.setCommentsNum(contentModel.getCommentsNum() - count);
            contentService.updateContentByCid(contentModel);
        }
    }

    /**
     * 更新评论的状态
     *
     * @param coid
     * @param status
     */
    @Override
    @CacheEvict(value = "commentCache", allEntries = true)
    public void updateCommentStatus(Integer coid, String status) {
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        commentMapper.updateCommentStatus(coid, status);
    }

    /**
     * 查找单条评论
     *
     * @param coid
     * @return
     */
    @Override
    @Cacheable(value = "commentCache", key = "'commentsByCId_'+#p0")
    public CommentModel getCommentById(Integer coid) {
        if (coid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return commentMapper.getCommentById(coid);
    }

    /**
     * 根据文章获取评论列表--只显示通过审核的评论--正常状态
     *
     * @param cid
     * @return
     */
    @Override
    @Cacheable(value = "commentCache", key = "'commentsByCId_'+#p0")
    public List<CommentModel> getCommentByCId(Integer cid) {
        if (cid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return commentMapper.getCommentsByCId(cid);
    }

    /**
     * 根据条件获取评论列表
     *
     * @param commentCond
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable(value = "commentCache", key = "'commentsByCond_'+#p1")
    public PageInfo<CommentModel> getCommentByCond(CommentCond commentCond, int pageNum, int pageSize) {
        if (commentCond==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        PageHelper.startPage(pageNum,pageSize);
        List<CommentModel> comments = commentMapper.getCommentsByCond(commentCond);
        PageInfo<CommentModel> pageInfo = new PageInfo<>(comments);
        return pageInfo;
    }
}
