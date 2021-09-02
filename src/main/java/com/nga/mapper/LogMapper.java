package com.nga.mapper;

import com.nga.dao.LogDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LogMapper {
    /**
     * 获取日志
     * @return
     */
    public List<LogDAO> getLogs();

    /**
     * 添加日志
     * @param logDAO
     */
    public void addLog(LogDAO logDAO);
}
