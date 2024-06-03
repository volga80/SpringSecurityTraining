package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.dao.UserRepository;
import com.volga.SpringSecurityTraining.domain.Role;
import com.volga.SpringSecurityTraining.domain.User;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void save_NewUser_SuccessfullySaved() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser", "password", "test@example.com");

        assertTrue(userService.save(signUpRequest));
    }

    @Test
    void save_UserWithSameUsername_AlreadyExists() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser", "password", "test@example.com");

        when(userRepository.findFirstByUsername(signUpRequest.getUsername())).thenReturn(new User(
                0, "testuser", "password", "test@example.com", null));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.save(signUpRequest));
        assertEquals("Пользователь с таким именем уже есть!", thrown.getMessage());
    }


    @Test
    void save_UserWithSameEmail_AlreadyExists() {
        SignUpRequest signUpRequest = new SignUpRequest("testuser", "password", "test@example.com");

        when(userRepository.findFirstByUsername("testuser")).thenReturn(null);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.save(signUpRequest));
    }


    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        User user = new User(0, "testuser", "password", "test@example.com", Role.USER);

        when(userRepository.findFirstByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {
        when(userRepository.findFirstByUsername("testuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testuser"));
    }
}
