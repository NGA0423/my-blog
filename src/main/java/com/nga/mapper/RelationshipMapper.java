package com.nga.mapper;

import com.nga.dao.RelationshipDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RelationshipMapper {
    /**
     * 添加
     * @param relationshipDAO
     * @return
     */
    public int addRelationShip(RelationshipDAO relationshipDAO);

    /**
     * 根据文章编号删除和meta编号关联
     * @param cid
     * @param mid
     * @return
     */
    public int deleteRelationShipById(@Param("cid") Integer cid,@Param("mid")Integer mid);

    /**
     * 根据文章编号删除关联
     * @param cid
     * @return
     */
    public int deleteRelationShipByCId(@Param("cid") Integer cid);

    /**
     * 根据meta编号删除关联
     * @param mid
     * @return
     */
    public int deleteRelationShipByMid(@Param("mid") Integer mid);

    /**
     * 更新
     * @param relationshipDAO
     * @return
     */
    public int updateRelationShip(RelationshipDAO relationshipDAO);

    /**
     * 根据文章编号获取关联
     * @param cid
     * @return
     */
    public List<RelationshipDAO> getRelationShipByCid(@Param("cid")Integer cid);

    /**
     * 根据meta编号获取关联
     * @param mid
     * @return
     */
    public List<RelationshipDAO> getRelationShipByMid(@Param("mid")Integer mid);

    /**
     * 获取数量
     * @param cid
     * @param mid
     * @return
     */
    public Long getCountById(@Param("cid")Integer cid,@Param("mid")Integer mid);
}
