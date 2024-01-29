package com.example.notepadApp.service;

import com.example.notepadApp.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User createUser(User user);

    Optional<User> findUserByEmail(String email);

}

