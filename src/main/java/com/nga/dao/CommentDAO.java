package com.nga.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDAO {
    /**
     * 表主键
     */
    private Integer coid;
    /**
     * 关联字段
     */
    private Integer cid;
    /**
     * 评论生成时的GMT unix时间戳
     */
    private Integer created;
    /**
     * 评论者
     */
    private String author;
    /**
     * 评论所属用户ID
     */
    private Integer authorId;
    /**
     * 评论所属内容作者ID
     */
    private Integer ownerId;
    /**
     * 评论者邮件
     */
    private String mail;
    /**
     * 评论者网址
     */
    private String url;
    /**
     * 评论这IP地址
     */
    private String ip;
    /**
     * 评论者客户端
     */
    private String agent;
    /**
     * 评论类型
     */
    private String type;
    /**
     * 评论状态
     */
    private String status;
    /**
     * 父级评论
     */
    private Integer parent;
    /**
     * 评论内容
     */
    private String content;
}
