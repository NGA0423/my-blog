package com.nga.service;

import com.nga.dao.MetaDAO;
import com.nga.dao.MetaUtil;
import com.nga.dao.cond.MetaCond;

import java.util.List;

public interface MetaService {
    /**
     * 添加项目
     * @param metaDAO
     */
    public void addMeta(MetaDAO metaDAO);

    /**
     * 添加
     * @param type
     * @param name
     * @param mid
     */
    public void saveMeta(String type,String name,Integer mid);

    /**
     * 批量添加
     * @param cid
     * @param name
     * @param type
     */
    public void addMetas(Integer cid,String name,String type);

    /**
     * 添加或者更新
     * @param cid
     * @param name
     * @param type
     */
    public void saveOrUpdate(Integer cid,String name,String type);

    /**
     * 删除项目
     * @param mid
     */
    public void deleteMetaById(Integer mid);

    /**
     * 更新项目
     * @param metaDAO
     */
    public void updateMeta(MetaDAO metaDAO);

    /**
     * 根据编号获取项目
     * @param mid
     * @return
     */
    public MetaDAO getMetaById(Integer mid);

    /**
     * 获取所有的项目
     * @param metaCond
     * @return
     */
    public List<MetaDAO> getMetas(MetaCond metaCond);

    /**
     * 根据类型查询项目列表，带项目下面的文章
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    public List<MetaUtil> getMetaList(String type, String orderby, int limit);
}
