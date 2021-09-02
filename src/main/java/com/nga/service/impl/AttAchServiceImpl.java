package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nga.constan.ErrorConstant;
import com.nga.dao.AttAchDAO;
import com.nga.mapper.AttAchMapper;
import com.nga.model.AttAchModel;
import com.nga.service.AttAchService;
import com.nga.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件服务实现
 */
@Service
public class AttAchServiceImpl implements AttAchService {
    @Autowired
    private AttAchMapper attAchMapper;

    /**
     * 添加单个附件信息
     *
     * @param attAchModel
     */
    @Override
    @CacheEvict(value = {"attCaches","attCache"},allEntries = true,beforeInvocation = true)
    public void addAttAch(AttAchModel attAchModel) {
        if (attAchModel==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchMapper.addAttAch(attAchModel);
    }

    /**
     * 批量添加附件信息
     *
     * @param list
     */
    @Override
    @CacheEvict(value = {"attCaches","attCache"},allEntries = true,beforeInvocation = true)
    public void batchAddAch(List<AttAchModel> list) {
        if (list==null||list.size()==0)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchMapper.batchAddAch(list);
    }

    /**
     * 根据主键编号删除附件信息
     *
     * @param id
     */
    @Override
    @CacheEvict(value = {"attCaches","attCache"},allEntries = true,beforeInvocation = true)
    public void deleteAttAch(Integer id) {
        if (id==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchMapper.deleteAttAch(id);
    }

    /**
     * 更新附件信息
     *
     * @param attAchModel
     */
    @Override
    @CacheEvict(value = {"attCaches","attCache"},allEntries = true,beforeInvocation = true)
    public void updateAttAch(AttAchModel attAchModel) {
        if (attAchModel==null||attAchModel.getId()==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        attAchMapper.updateAttAch(attAchModel);
    }

    /**
     * 根据主键获取附件信息
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "attCahe",key = "'atts'+#p0")
    public AttAchDAO getAttAchById(Integer id) {
        if (id==null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return attAchMapper.getAttAchById(id);
    }

    /**
     * 获取所有的附件信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable(value = "attCahe",key = "'atts'+#p0")
    public PageInfo<AttAchDAO> getAtts(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AttAchDAO> atts = attAchMapper.getAtts();
        PageInfo<AttAchDAO> pageInfo = new PageInfo<>(atts);
        return pageInfo;
    }
}
