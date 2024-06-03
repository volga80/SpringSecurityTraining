package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.domain.JwtAuthResponse;
import com.volga.SpringSecurityTraining.dto.SignInRequest;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;

public interface AuthService {

    JwtAuthResponse authenticateUser(SignInRequest signInRequest);

    JwtAuthResponse signUp(SignUpRequest signUpRequest);
}
