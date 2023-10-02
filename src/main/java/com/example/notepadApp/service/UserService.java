package com.example.notepadApp.service;

import com.example.notepadApp.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);

    User findUserByEmail(String email);

    List<User> findAllUsers();
    boolean existsByEmail(String email);
}

