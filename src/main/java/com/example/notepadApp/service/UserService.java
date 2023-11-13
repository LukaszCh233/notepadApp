package com.example.notepadApp.service;

import com.example.notepadApp.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User saveUser(User user);

    Optional<User> findUserByEmail(String email);

    List<User> findAllUsers();

    boolean existsByEmail(String email);
}

