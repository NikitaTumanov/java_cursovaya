package com.example.demo;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> captor;

    private User user1, user2, user3;
    @BeforeEach
    void init() {
        user1 = new User();
        user1.setId(1);
        user1.setEmail("email");
        user1.setName("user1");
        user1.setPassword("password");
        user1.setType("role");

        user2 = new User();
        user2.setId(2);
        user2.setEmail("email");
        user2.setName("user2");
        user2.setPassword("password");
        user2.setType("role");

        user3 = new User();
        user3.setId(3);
        user3.setEmail("email");
        user3.setName("user3");
        user3.setPassword("password");
        user3.setType("role");
    }
    @Test
    void getUserByUsername() {
        Mockito.when(userRepository.findByName("user2")).thenReturn(user2);
        assertEquals(user2, userRepository.findByName("user2"));
    }
    @Test
    void create() {
        userService.create("user4", "password", "email","type");
        Mockito.verify(userRepository).save(captor.capture());
        User captured = captor.getValue();
        assertEquals("user4", captured.getUsername());
    }
    @Test
    void getAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));
        assertEquals(List.of(user1, user2, user3), userRepository.findAll());
    }
    @Test
    void deleteUser() {
        userService.delete("user1");
        Mockito.verify(userRepository).deleteByName("user1");
    }
}