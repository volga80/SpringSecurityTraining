package com.volga.SpringSecurityTraining.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    boolean isTokenValid(String token, UserDetails user);

    String generateToken(Authentication authentication);

    public String getUsernameByToken(String token);

}
