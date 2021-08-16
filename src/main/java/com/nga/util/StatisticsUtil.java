package com.nga.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台统计对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsUtil {
    /**
     * 文章数
     */
    private Long articles;
    /**
     * 评论数
     */
    private Long comments;
    /**
     * 链接数
     */
    private Long links;
    /**
     * 附件数
     */
    private Long attachs;
}
