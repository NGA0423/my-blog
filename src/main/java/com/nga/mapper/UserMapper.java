package com.nga.mapper;

import com.nga.dao.UserDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    UserDAO queryUserByName(String username);

    String addUser(UserDAO user);
}
