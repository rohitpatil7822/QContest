package com.crio.contest.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.crio.contest.entity.User;
import com.crio.contest.repository.UserRepository;
import com.crio.contest.service.UserService;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        
        List<User> users = userRepository.findAll();

        users.sort((u1,u2)-> Integer.compare(u2.getScore(), u1.getScore()));
        return users;
    }

    @Override
    public User createUser(User user) {
        user.setScore(0);
        user.getBadges().clear();
        user.assignBadges();
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {

        User user =  userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));

        return user;
        
    }


    @Override
    public String deleteUser(Long userId) {
       
        User user =  userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found"));

        userRepository.delete(user);

        return "User with id " + userId + " is successfully deleted";
    }

    @Override
    public User updateUser(Long userId, User user) {
        
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    
        if (user.getUserName() != null && !user.getUserName().isBlank()) {
            existingUser.setUserName(user.getUserName());
        }

        if (user.getScore() != existingUser.getScore()) {
            existingUser.setScore(user.getScore());
            existingUser.assignBadges();
        }
     
        return userRepository.save(existingUser);
    }
    
    
}
