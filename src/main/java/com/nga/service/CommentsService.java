package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.cond.CommentCond;
import com.nga.model.CommentModel;

import java.util.List;

/**
 * 评论服务
 */
public interface CommentsService {
    /**
     * 新增评论
     * @param commentModel
     */
    public void addComment(CommentModel commentModel);

    /**
     * 删除评论
     * @param coid
     */
    public void deleteComment(Integer coid);

    /**
     * 更新评论的状态
     * @param coid
     * @param status
     */
    public void updateCommentStatus(Integer coid,String status);

    /**
     * 查找单条评论
     * @param coid
     * @return
     */
    public CommentModel getCommentById(Integer coid);

    /**
     * 根据文章获取评论列表--只显示通过审核的评论--正常状态
     * @param cid
     * @return
     */
    public List<CommentModel> getCommentByCId(Integer cid);

    /**
     * 根据条件获取评论列表
     * @param commentCond
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<CommentModel> getCommentByCond(CommentCond commentCond,int pageNum,int pageSize);
}
