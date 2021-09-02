package com.nga.service;

import com.github.pagehelper.PageInfo;
import com.nga.dao.LogDAO;

public interface LogService {
    /**
     * 添加
     * @param action
     * @param data
     * @param ip
     * @param authorId
     */
    public void addLog(String action,String data,String ip,Integer authorId);
    /**
     * 获取日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<LogDAO> getLogs(int pageNum, int pageSize);
}
