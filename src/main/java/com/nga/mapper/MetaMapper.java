package com.nga.mapper;

import com.nga.dao.MetaDAO;
import com.nga.dao.MetaUtil;
import com.nga.dao.cond.MetaCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MetaMapper {
    /**
     * 添加项目
     * @param metaDAO
     * @return
     */
    public int addMeta(MetaDAO metaDAO);
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

    /**
     * 根据条件查询
     * @param metaCond
     * @return
     */
    public List<MetaDAO> getMetasByCond(MetaCond metaCond);

    /**
     * 根据编号获取项目
     * @param mid
     * @return
     */
    public MetaDAO getMetaById(@Param("mid")Integer mid);

    /**
     * 更新项目
     * @param metaDAO
     * @return
     */
    public int updateMeta(MetaDAO metaDAO);

    /**
     * 删除项目
     * @param mid
     * @return
     */
    public int deleteMetaById(@Param("mid")Integer mid);
}
