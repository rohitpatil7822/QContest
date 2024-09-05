package com.crio.contest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.crio.contest.entity.User;
import com.crio.contest.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    public static final String USER_API_ENDPOINT = "/users";

    @Autowired
    private UserService userService;


    @GetMapping(USER_API_ENDPOINT) // Get All Users -------------------------

    public ResponseEntity<Object> getAllUsers(){

        List<User> users =  userService.getAllUsers();

        if (users.isEmpty()) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No users found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping(USER_API_ENDPOINT+"/{userId}") // Get  User by Id -------------------

    public ResponseEntity<Object> getUser(@Valid @PathVariable Long userId){

        try {
            
            User user = userService.getUser(userId);
    
            return new ResponseEntity<>(user , HttpStatus.OK);
        } catch (ResponseStatusException e) {

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", e.getStatusCode().value());
            errorResponse.put("error", e.getReason());
            
            return new ResponseEntity<>(errorResponse , e.getStatusCode());
        }
    }


    @PostMapping(USER_API_ENDPOINT) // Create User ---------------------------------
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        ResponseEntity<Object> existingUser = getUser(user.getUserId());

        if (existingUser.getStatusCode() == HttpStatus.OK) {
            
            Map<String , Object> errorResponse = new HashMap<>();
            errorResponse.put("status" , HttpStatus.BAD_REQUEST);
            errorResponse.put("error" , "User with the same userId already exists");

            return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }
    

    @PutMapping(USER_API_ENDPOINT +"/{userId}") // Update User ---------------------------------
    public ResponseEntity<Object> updateUser(@Valid @PathVariable Long userId ,@RequestBody User user){

        Map<String , Object> errorResponse = new HashMap<>();

        if ((user.getScore() < 1 )|| (user.getScore() > 100)) {

            errorResponse.put("status" , HttpStatus.BAD_REQUEST);
            errorResponse.put("error" , "Invalid Score Input! Should be greater than 1 and less than 100");

            return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);      
        }

        if (user.getUserName() == null || user.getUserName().isBlank()) {
            errorResponse.put("status", HttpStatus.BAD_REQUEST);
            errorResponse.put("error", "User Name cannot be blank");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User newUser = userService.updateUser(userId , user);

        errorResponse.put("status" , HttpStatus.OK);
        errorResponse.put("message" , "User Updated Successfully");
        errorResponse.put("User" , newUser);

        return new ResponseEntity<>(errorResponse,HttpStatus.OK);
    }
    
    @DeleteMapping(USER_API_ENDPOINT +"/{userId}") // Delete User ---------------------------------

    public ResponseEntity<Object> deleteUser(@Valid @PathVariable Long userId){

        Map<String , Object> errorResponse = new HashMap<>();
        try {

            String response = userService.deleteUser(userId);
            errorResponse.put("status" , HttpStatus.OK);
            errorResponse.put("error" , response);
            return new ResponseEntity<>(errorResponse , HttpStatus.OK);

        }catch (ResponseStatusException e){

            errorResponse.put("status" , HttpStatus.BAD_REQUEST);
            errorResponse.put("error" , e.getReason());

            return new ResponseEntity<>(errorResponse,e.getStatusCode());
        }
    }
    
}
