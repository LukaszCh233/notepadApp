package com.example.notepadApp.controller;

import com.example.notepadApp.config.HelpJwt;
import com.example.notepadApp.entities.User;
import com.example.notepadApp.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final HelpJwt helpJwt;
    PasswordEncoder passwordEncoder;


    public UserController(UserService userService, HelpJwt helpJwt, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.helpJwt = helpJwt;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user) {
        boolean checkEmail = userService.existsByEmail(user.getEmail());
        if (checkEmail) {
            logger.warn("Email already exists: {}", user.getEmail());
            return new ResponseEntity<>("Email exists", HttpStatus.BAD_REQUEST);
        } else {

            User create = userService.saveUser(user);
            logger.info("User registered successfully: {}", create.getEmail());
            return new ResponseEntity<>(create, HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody User user) {
        boolean checkUser = userService.existsByEmail(user.getEmail());
        if (!checkUser) {
            logger.warn("Email not exists: {}", user.getEmail());
            return new ResponseEntity<>("Email not exists", HttpStatus.BAD_REQUEST);
        }
        User storedUser = userService.findUserByEmail(user.getEmail());
        if (passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
            logger.info("User logged in successfully: {}", storedUser.getEmail());
            String jwtToken = helpJwt.generateToken(storedUser);

            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            logger.warn("Incorrect password for user: {}", storedUser.getEmail());
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/users")
    List<User> users() {
        return userService.findAllUsers();
    }

}
