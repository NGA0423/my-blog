package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.LogDAO;

public interface LogService {
    /**
     * 获取日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<LogDAO> getLogs(int pageNum, int pageSize);
}
