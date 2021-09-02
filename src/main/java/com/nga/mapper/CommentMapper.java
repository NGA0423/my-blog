package com.nga.mapper;

import com.nga.dao.CommentDAO;
import com.nga.dao.cond.CommentCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    /**
     * 根据条件获取评论列表
     * @param commentCond
     * @return
     */
    public List<CommentDAO> getCommentByCond(CommentCond commentCond);
    /**
     * 获取评论数量
     * @return
     */
    public Long getCommentsCount();

    /**
     * 根据文章编号获取评论列表
     * @param cid
     * @return
     */
    public List<CommentDAO> getCommentsByCId(@Param("cid")Integer cid);

    /**
     * 删除评论
     * @param coid
     * @return
     */
    public int deleteComment(@Param("coid")Integer coid);
}
