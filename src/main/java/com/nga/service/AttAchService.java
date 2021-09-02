package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.AttAchDAO;
import com.nga.model.AttAchModel;

import java.util.List;

/**
 * 附件服务
 */
public interface AttAchService {
    /**
     * 添加单个附件信息
     *
     * @param attAchModel
     */
    public void addAttAch(AttAchModel attAchModel);

    /**
     * 批量添加附件信息
     *
     * @param list
     */
    public void batchAddAch(List<AttAchModel> list);

    /**
     * 根据主键编号删除附件信息
     *
     * @param id
     */
    public void deleteAttAch(Integer id);

    /**
     * 更新附件信息
     *
     * @param attAchModel
     */
    public void updateAttAch(AttAchModel attAchModel);

    /**
     * 根据主键获取附件信息
     *
     * @param id
     * @return
     */
    public AttAchDAO getAttAchById(Integer id);

    /**
     * 获取所有的附件信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<AttAchDAO> getAtts(int pageNum, int pageSize);
}
