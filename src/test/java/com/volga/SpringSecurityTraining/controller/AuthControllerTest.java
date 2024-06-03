package com.volga.SpringSecurityTraining.controller;

import com.volga.SpringSecurityTraining.domain.JwtAuthResponse;
import com.volga.SpringSecurityTraining.dto.SignInRequest;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import com.volga.SpringSecurityTraining.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController controller;

    @Mock
    AuthService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void signUp_ShouldReturnJwtAuthResponse() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("testuser", "password", "test@example.com");
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse("test-jwt-token");

        when(service.signUp(any(SignUpRequest.class))).thenReturn(jwtAuthResponse);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"password\", \"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"test-jwt-token\"}"));
    }

    @Test
    void login_ShouldReturnJwtAuthResponse() throws Exception {
        SignInRequest signInRequest = new SignInRequest("testuser", "password");
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse("test-jwt-token");

        when(service.authenticateUser(any(SignInRequest.class))).thenReturn(jwtAuthResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"test-jwt-token\"}"));
    }
}