package com.nga.mapper;

import com.nga.dao.MetaUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MetaMapper {
    /**
     * 根据类型获取meta数量
     * @param type
     * @return
     */
    public Long getMetasCountByType(@Param("type") String type);

    /**
     * 根据sql查询
     */
    public List<MetaUtil> selectFromSql(Map<String, Object> paraMap);
}
