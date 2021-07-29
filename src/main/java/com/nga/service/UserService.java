package com.nga.service;

import com.nga.dao.User;

public interface UserService {
    public User queryUserByName(String username);
    public String addUser(User user);
}
