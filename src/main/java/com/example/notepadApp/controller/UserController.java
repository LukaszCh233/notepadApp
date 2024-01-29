package com.example.notepadApp.controller;

import com.example.notepadApp.config.HelpJwt;
import com.example.notepadApp.entities.User;
import com.example.notepadApp.entities.UserDTO;
import com.example.notepadApp.service.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final HelpJwt helpJwt;
    PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userService, HelpJwt helpJwt, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.helpJwt = helpJwt;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody User user) {

        User createUser = userService.createUser(user);
        UserDTO userDTO = userService.mapUserToUserDTO(createUser);
        logger.info("User registered successfully: {}", userDTO.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody User user) {

        User registeredUser = userService.findUserByEmail(user.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Not found User"));

        if (!passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect email or password");
        }
        String jwtToken = helpJwt.generateToken(registeredUser);
        logger.info("User logged in successfully: {}", registeredUser.getEmail());
        return ResponseEntity.ok(jwtToken);
    }
}
