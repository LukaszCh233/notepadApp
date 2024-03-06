package com.example.notepadApp.repositoryTests;

import com.example.notepadApp.entities.Role;
import com.example.notepadApp.entities.User;
import com.example.notepadApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveUser_Test() {
        //Given
        User user = new User(3, "test", "test@@@", "testPassword", Role.USER);

        userRepository.save(user);

        //when
        List<User> users = userRepository.findAll();

        //Then
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void shouldFindUserByEmail_Test() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);

        userRepository.save(user);

        //When
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        //Then
        Assertions.assertTrue(foundUser.isPresent());
        String foundEmail = foundUser.get().getEmail();
        Assertions.assertEquals(foundEmail, user.getEmail());
    }

    @Test
    public void shouldFindAllUsers_Test() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);
        User user1 = new User(2, "test1", "test@1", "testPassword1", Role.USER);

        userRepository.save(user);
        userRepository.save(user1);

        //When
        List<User> users = userRepository.findAll();

        //Then
        Assertions.assertEquals(users.size(), 2);
    }

    @Test
    public void shouldCheckExistsUserByEmail_Test() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);

        userRepository.save(user);

        //When
        boolean check = userRepository.existsByEmail(user.getEmail());

        //Then
        Assertions.assertTrue(check, "User exists");
    }
}
