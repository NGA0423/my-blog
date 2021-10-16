package com.nga.mapper;

import com.nga.dao.LogDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LogMapper {
    /**
     * 获取日志
     * @return
     */
    List<LogDAO> getLogs();

    /**
     * 添加日志
     * @param logDAO
     */
    void addLog(LogDAO logDAO);

    /**
     * 删除日志
     * @param id
     * @return
     */
    int deleteLogById(@Param("id") Integer id);
}
