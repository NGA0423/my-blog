package com.nga.mapper;

import com.nga.dao.UserDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    /**
     * 添加用户
     * @param user
     * @return
     */
    public String addUser(UserDAO user);

    /**
     * 更新用户信息
     * @param userDAO
     * @return
     */
    int updateUserInfo(UserDAO userDAO);

    /**
     * 根据主键编号获取用户信息
     * @param id
     * @return
     */
    UserDAO getUserInfoById(@Param("id")Integer id);

    /**
     * 根据用户名密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    UserDAO getUserInfoByCond(@Param("username")String username,@Param("password")String password);
}
