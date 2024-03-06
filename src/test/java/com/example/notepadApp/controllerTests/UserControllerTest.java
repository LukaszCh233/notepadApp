package com.example.notepadApp.controllerTests;

import com.example.notepadApp.config.HelpJwt;
import com.example.notepadApp.entities.Role;
import com.example.notepadApp.entities.User;
import com.example.notepadApp.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private HelpJwt helpJwt;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PasswordEncoder passwordEncoder;

    public UserControllerTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("testName");
        user.setEmail("testEmail");
        user.setPassword("testPassword");
        user.setRole(Role.USER);

        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userService.createUser(user)).thenReturn(user);
        when(userService.mapUserToUserDTO(user)).thenCallRealMethod();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"testName\",\"email\":\"testEmail\",\"password\":\"testPassword\",\"role\":\"USER\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.email").value("testEmail"));
    }

    @Test
    void testLoginUser_Success() throws Exception {
        User user = new User();
        user.setEmail("testEmail");
        user.setPassword("testPassword");

        User storedUser = new User();
        storedUser.setEmail("testEmail");
        storedUser.setPassword(passwordEncoder.encode("testPassword"));

        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches(user.getPassword(), storedUser.getPassword())).thenReturn(true);
        when(helpJwt.generateToken(storedUser)).thenReturn("jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"testEmail\",\"password\":\"testPassword\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("jwtToken"));
    }
}