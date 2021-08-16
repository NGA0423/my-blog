package com.nga.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AttAchMapper {
    /**
     * 查找附件数量
     * @return
     */
    public Long getAttsCount();
}
