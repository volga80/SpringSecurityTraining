package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.domain.User;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import com.volga.SpringSecurityTraining.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean save(SignUpRequest signUpRequest);

    List<UserDTO> getAll();
}
