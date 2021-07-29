package com.nga.service.impl;

import com.nga.dao.User;
import com.nga.mapper.UserMapper;
import com.nga.service.UserService;
import com.nga.util.MD5_Shiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = true)
    UserMapper userMapper;

    @Override
    public User queryUserByName(String username) {
        return userMapper.queryUserByName(username);
    }

    @Override
    public String addUser(User user) {
        // 查询注册的用户名是否存在
        User info = userMapper.queryUserByName(user.getUsername());
        if (info!=null){
            return "134";
        }else {
            try {
                // 随机生成UUID，作为盐值
                String uuid = UUID.randomUUID().toString().replace("-", "");
                // 调用 工具类，生成加密后的密码
                String md5 = MD5_Shiro.encryptPassword("MD5", user.getPassword(), uuid, 1024);
                user.setPassword(md5);
                user.setSalt(uuid);
                userMapper.addUser(user);
                return "200";
            }catch (Exception e){
                e.printStackTrace();
                return "502";
            }
        }

    }
}
