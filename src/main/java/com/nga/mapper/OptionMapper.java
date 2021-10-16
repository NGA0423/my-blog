package com.nga.mapper;

import com.nga.model.OptionsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网站配置
 */
@Mapper
public interface OptionMapper {
    /**
     * 删除网站配置
     * @param name
     * @return
     */
    int deleteOptionByName(@Param("name") String name);

    /**
     * 更新网站配置
     * @param options
     * @return
     */
    int updateOptionByName(OptionsModel options);

    /**
     * 根据名称获取网站配置
     * @param name
     * @return
     */
    OptionsModel getOptionByName(@Param("name")String name);

    /**
     * 获取网站全部配置
     * @return
     */
    List<OptionMapper> getOptions();
}
