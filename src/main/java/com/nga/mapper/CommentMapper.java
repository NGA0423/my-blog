package com.nga.mapper;

import com.nga.dao.cond.CommentCond;
import com.nga.model.CommentModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    /**
     * 新增评论
     * @param commentModel
     * @return
     */
    public int addComment(CommentModel commentModel);
    /**
     * 根据条件获取评论列表
     * @param commentCond
     * @return
     */
    public List<CommentModel> getCommentByCond(CommentCond commentCond);
    /**
     * 获取评论数量
     * @return
     */
    public Long getCommentsCount();

    /**
     * 获取单条评论
     * @param cid
     * @return
     */
    public CommentModel getCommentById(@Param("cid")Integer cid);

    /**
     * 根据文章编号获取评论列表
     * @param cid
     * @return
     */
    public List<CommentModel> getCommentsByCId(@Param("cid")Integer cid);

    /**
     * 删除评论
     * @param coid
     * @return
     */
    public int deleteComment(@Param("coid")Integer coid);

    /**
     * 更新评论状态
     * @param coid
     * @param status
     * @return
     */
    public int updateCommentStatus(@Param("coid")Integer coid,@Param("status")String status);

    /**
     * 根据条件获取评论列表
     * @param commentCond
     * @return
     */
    public List<CommentModel> getCommentsByCond(CommentCond commentCond);
}
