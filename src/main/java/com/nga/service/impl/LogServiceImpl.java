package com.nga.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nga.dao.LogDAO;
import com.nga.mapper.LogMapper;
import com.nga.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 添加
     *
     * @param action
     * @param data
     * @param ip
     * @param authorId
     */
    @Override
    public void addLog(String action, String data, String ip, Integer authorId) {
        LogDAO logDAO = new LogDAO();
        logDAO.setAuthorId(authorId);
        logDAO.setAction(action);
        logDAO.setData(data);
        logDAO.setIp(ip);
        logMapper.addLog(logDAO);
    }

    /**
     * 获取日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<LogDAO> getLogs(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<LogDAO> logs = logMapper.getLogs();
        PageInfo<LogDAO> pageInfo = new PageInfo<>(logs);
        return pageInfo;
    }
}
