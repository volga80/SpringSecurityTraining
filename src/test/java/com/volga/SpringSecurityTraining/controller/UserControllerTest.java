package com.volga.SpringSecurityTraining.controller;

import com.volga.SpringSecurityTraining.config.JwtAuthenticationFilter;
import com.volga.SpringSecurityTraining.dto.UserDTO;
import com.volga.SpringSecurityTraining.service.JwtTokenService;
import com.volga.SpringSecurityTraining.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService service;

    @MockBean
    JwtTokenService jwtTokenService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getName_ShouldReturnUserName() throws Exception {
        mockMvc.perform(get("/user/myname")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getUsers_ShouldReturnListOfUsers() throws Exception {
        List<UserDTO> users = Arrays.asList(new UserDTO("user1", "user1@example.com"),
                new UserDTO("user2", "user2@example.com"));

        when(service.getAll()).thenReturn(users);

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getSimpleText_ShouldReturnGreeting() throws Exception {
        mockMvc.perform(get("/user/text"))
                .andExpect(status().isOk());
    }
}