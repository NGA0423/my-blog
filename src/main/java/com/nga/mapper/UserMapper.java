package com.nga.mapper;

import com.nga.dao.UserDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public UserDAO queryUserByName(String username);

    public String addUser(UserDAO user);
}
