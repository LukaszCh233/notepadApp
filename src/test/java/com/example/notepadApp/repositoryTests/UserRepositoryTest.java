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
    public void shouldSaveUserTest() {
        //Given
        User user = new User(3, "test", "test@@@", "testPassword", Role.USER);

        //when
        userRepository.save(user);

        //Then
        List<User> users = userRepository.findAll();
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void shouldFindUserByEmailTest() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);

        //When
        userRepository.save(user);

        //Then
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        Assertions.assertTrue(foundUser.isPresent());
        String foundEmail = foundUser.get().getEmail();
        Assertions.assertEquals(foundEmail, user.getEmail());
    }

    @Test
    public void shouldFindAllUsers() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);
        User user1 = new User(2, "test1", "test@1", "testPassword1", Role.USER);

        //When
        userRepository.save(user);
        userRepository.save(user1);

        //Then
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(users.size(), 2);
    }

    @Test
    public void shouldCheckExistsUserByEmailTest() {
        //Given
        User user = new User(1, "test", "test@", "testPassword", Role.USER);
        //When
        userRepository.save(user);
        //Then
        boolean check = userRepository.existsByEmail(user.getEmail());

        Assertions.assertTrue(check, "User exists");

    }
}
