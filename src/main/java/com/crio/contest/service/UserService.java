package com.crio.contest.service;

import java.util.List;

import com.crio.contest.entity.User;


// import com.crio.contest.entity.User;


public interface  UserService {

    public List<User> getAllUsers();

    public User createUser(User user);

    public User getUser(Long userId);

    public User updateUser(Long userId , User user);

    public String deleteUser(Long userId);
    
}
