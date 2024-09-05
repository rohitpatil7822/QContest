package com.crio.contest.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.crio.contest.entity.User;
import com.crio.contest.repository.UserRepository;
import com.crio.contest.service.implementation.UserServiceImp;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImp userServiceImp ;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUserId(1L);
        user.setUserName("test_user");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userServiceImp.createUser(user);
        assertNotNull(createdUser);
        assertEquals(0, createdUser.getScore());
        assertTrue(createdUser.getBadges().isEmpty());
    }

    @Test
    public void testUpdateUserScore() {

        // Create a user to be returned by findById
        User existingUser = new User();
        existingUser.setUserId(1L);
        existingUser.setUserName("old_user");
        existingUser.setScore(0);
        existingUser.setBadges(new HashSet<>()); // Initially empty
        
        User user = new User();
        user.setUserId(1L);
        user.setUserName("test_user");
        user.setScore(50);
        user.setBadges(new HashSet<>(Collections.singletonList("Code Champ"))); // Expected badges

        when(userRepository.findById((long) 1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(user);

        User updatedUser = userServiceImp.updateUser(1L, user);

        System.out.println("Updated User: " + updatedUser);

        assertEquals(50, updatedUser.getScore());
        assertTrue(updatedUser.getBadges().contains("Code Champ"));
    }

    @Test
    public void testGetUserByIdNotFound() {
        User user = new User();
        user.setUserName("test_user");
        user.setScore(50);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userServiceImp.updateUser(1L, user);
        });

        String expectedMessage = "User Not Found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}