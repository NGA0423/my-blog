package com.nga.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDAO {
    /**
     * 文章主键
     */
    private Integer cid;
    /**
     * 项目编号
     */
    private Integer mid;

}
