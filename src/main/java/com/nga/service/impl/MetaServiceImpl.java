package com.nga.service.impl;

import com.nga.constan.ErrorConstant;
import com.nga.constan.WebConst;
import com.nga.dao.ContentDAO;
import com.nga.dao.MetaDAO;
import com.nga.dao.MetaUtil;
import com.nga.dao.RelationshipDAO;
import com.nga.dao.cond.MetaCond;
import com.nga.mapper.MetaMapper;
import com.nga.mapper.RelationshipMapper;
import com.nga.service.ContentService;
import com.nga.service.MetaService;
import com.nga.util.BusinessException;
import com.nga.util.TypesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MetaServiceImpl implements MetaService {
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private RelationshipMapper relationshipMapper;
    @Autowired
    private ContentService contentService;

    /**
     * 添加项目
     *
     * @param metaDAO
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMeta(MetaDAO metaDAO) {
        if (metaDAO == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaMapper.addMeta(metaDAO);
    }

    /**
     * 添加
     *
     * @param type
     * @param name
     * @param mid
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            MetaCond metaCond = new MetaCond();
            metaCond.setName(name);
            metaCond.setType(type);
            List<MetaDAO> metas = metaMapper.getMetasByCond(metaCond);
            if (metas == null || metas.size() == 0) {
                MetaDAO metaDAO = new MetaDAO();
                metaDAO.setName(name);
                if (mid != null) {
                    MetaDAO meta = metaMapper.getMetaById(mid);
                    if (meta != null) {
                        metaDAO.setMid(mid);
                        metaMapper.updateMeta(metaDAO);
                        // 更新原有的文章类型
                        if (meta != null) {
                            contentService.updateCategory(meta.getName(), name);
                        }
                    } else {
                        metaDAO.setType(type);
                        metaMapper.addMeta(metaDAO);
                    }
                } else {
                    throw BusinessException.withErrorCode(ErrorConstant.Meta.META_IS_EXIST);
                }
            }
        }

    }

    /**
     * 批量添加
     *
     * @param cid
     * @param name
     * @param type
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void addMetas(Integer cid, String name, String type) {
        if (cid == null) {
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(type)) {
            String[] nameArr = StringUtils.split(name, ",");
            for (String names : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }

    }

    /**
     * 添加或者更新
     *
     * @param cid
     * @param name
     * @param type
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void saveOrUpdate(Integer cid, String name, String type) {
        MetaCond metaCond = new MetaCond();
        metaCond.setName(type);
        metaCond.setType(type);
        List<MetaDAO> metas = this.getMetas(metaCond);
        int mid;
        MetaDAO metaDAO;
        if (metas.size() == 1) {
            MetaDAO mate = metas.get(0);
            mid = mate.getMid();
        } else if (metas.size() > 1) {
            throw BusinessException.withErrorCode(ErrorConstant.Meta.NOT_ONE_RESULT);
        } else {
            metaDAO = new MetaDAO();
            metaDAO.setSlug(name);
            metaDAO.setName(name);
            metaDAO.setType(type);
            this.addMeta(metaDAO);
            mid = metaDAO.getMid();
        }
        if (mid != 0) {
            Long count = relationshipMapper.getCountById(cid, mid);
            if (count == 0) {
                RelationshipDAO relationshipDAO = new RelationshipDAO();
                relationshipDAO.setCid(cid);
                relationshipDAO.setMid(mid);
                relationshipMapper.addRelationShip(relationshipDAO);
            }
        }
    }

    /**
     * 删除项目
     *
     * @param mid
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void deleteMetaById(Integer mid) {
        if (mid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        MetaDAO metaById = metaMapper.getMetaById(mid);
        if (metaById != null) {
            String type = metaById.getType();
            String name = metaById.getName();
            metaMapper.deleteMetaById(mid);
            // 删除相关的数据
            List<RelationshipDAO> relationShipByMid = relationshipMapper.getRelationShipByMid(mid);
            if (relationShipByMid != null && relationShipByMid.size() > 0) {
                for (RelationshipDAO relationshipDAO : relationShipByMid) {
                    ContentDAO articleById = contentService.getArticleById(relationshipDAO.getCid());
                    if (articleById != null) {
                        ContentDAO contentDAO = new ContentDAO();
                        contentDAO.setCid(relationshipDAO.getCid());
                        if (type.equals(TypesUtil.TAG.getType())) {
                            contentDAO.setCategories(reMeta(name, articleById.getCategories()));
                        }
                        if (type.equals(TypesUtil.TAG.getType())) {
                            contentDAO.setTags(reMeta(name, articleById.getTags()));
                        }
                        // 将删除的资源去除
                        contentService.updateArticleById(contentDAO);
                    }
                }
                relationshipMapper.deleteRelationShipByMid(mid);
            }
        }
    }

    /**
     * 更新项目
     *
     * @param metaDAO
     */
    @Override
    @CacheEvict(value = {"metaCaches", "metaCache"}, allEntries = true, beforeInvocation = true)
    public void updateMeta(MetaDAO metaDAO) {
        if (metaDAO == null || metaDAO.getMid() == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaMapper.updateMeta(metaDAO);
    }

    /**
     * 根据编号获取项目
     *
     * @param mid
     * @return
     */
    @Override
    @Cacheable(value = "metaCache", key = "'metaById_'+#p0")
    public MetaDAO getMetaById(Integer mid) {
        if (mid == null)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        return metaMapper.getMetaById(mid);
    }

    /**
     * 获取所有的项目
     *
     * @param metaCond
     * @return
     */
    @Override
    @Cacheable(value = "metaCaches", key = "'metas_'+#p0")
    public List<MetaDAO> getMetas(MetaCond metaCond) {
        return metaMapper.getMetasByCond(metaCond);
    }

    /**
     * 根据类型查询项目列表，带项目下面的文章
     *
     * @param type
     * @param orderby
     * @param limit
     * @return
     */
    @Override
    @Cacheable(value = "metaCaches", key = "'metas_'+#p0")
    public List<MetaUtil> getMetaList(String type, String orderby, int limit) {
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isNotBlank(orderby)) {
                orderby = "count desc,a.mid desc";
            }
            if (limit < 1 || limit > WebConst.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaMapper.selectFromSql(paraMap);
        }
        return null;
    }

    private String reMeta(String name, String metas) {
        String[] split = StringUtils.split(metas, ",");
        StringBuffer stringBuffer = new StringBuffer();
        for (String m : split) {
            if (!name.equals(m)) {
                stringBuffer.append(",").append(m);
            }
        }
        if (stringBuffer.length() > 0) {
            return stringBuffer.substring(1);
        }
        return "";
    }
}
