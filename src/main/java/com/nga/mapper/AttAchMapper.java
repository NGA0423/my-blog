package com.nga.mapper;

import com.github.pagehelper.PageInfo;
import com.nga.dao.AttAchDAO;
import com.nga.model.AttAchModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AttAchMapper {
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
     * @return
     */
    List<AttAchDAO> getAtts();

    /**
     * 查找附件数量
     *
     * @return
     */
    public Long getAttsCount();
}
