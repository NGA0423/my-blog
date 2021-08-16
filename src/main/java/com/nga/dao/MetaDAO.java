package com.nga.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDAO implements Serializable {
    /**
     * 项目主键
     */
    private Integer mid;
    /**
     * 名称
     */
    private String name;
    /**
     * 项目缩略名
     */
    private String slug;
    /**
     * 项目类型
     */
    private String type;
    /**
     * 对应文章类型
     */
    private String contentType;
    /**
     * 选项描述
     */
    private String description;
    /**
     * 项目排序
     */
    private Integer sort;

    private Integer parent;
}
