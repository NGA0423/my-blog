package com.nga.service;

import com.nga.dao.UserDAO;

public interface UserService {
    public UserDAO queryUserByName(String username);
    public String addUser(UserDAO user);
}
