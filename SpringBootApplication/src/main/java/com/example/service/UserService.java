package com.example.service;


import com.example.entity.User;

import java.util.List;

public interface UserService {

    public User createUser(User user);

    public List<User> getAllUsers();
}
