package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.domain.JwtAuthResponse;
import com.volga.SpringSecurityTraining.dto.SignInRequest;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @Mock
    JwtTokenService jwtTokenService;

    @Mock
    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;


    @Test
    void authenticateUser() {
        SignInRequest signInRequest = new SignInRequest("username", "password");
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenService.generateToken(any(Authentication.class))).thenReturn("test-jwt-token");

        JwtAuthResponse response = authServiceImpl.authenticateUser(signInRequest);

        assertEquals("test-jwt-token", response.getToken());
    }


    @Test
    void testAuthenticateUserThrowsException() {
        SignInRequest signInRequest = new SignInRequest("username", "wrong-password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Ошибка Аутентификации"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            authServiceImpl.authenticateUser(signInRequest);
        });

        assertEquals("Ошибка Аутентификации", thrownException.getMessage());
    }


    @Test
    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest("newuser", "newpassword", "newmail");

        when(userService.save(signUpRequest)).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("newuser", "newpassword"));
        when(jwtTokenService.generateToken(any(Authentication.class))).thenReturn("new-jwt-token");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("newuser", "newpassword"));

        JwtAuthResponse response = authServiceImpl.signUp(signUpRequest);

        assertEquals("new-jwt-token", response.getToken());
    }
}