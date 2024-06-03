package com.volga.SpringSecurityTraining.service;

import com.volga.SpringSecurityTraining.domain.JwtAuthResponse;
import com.volga.SpringSecurityTraining.dto.SignInRequest;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse authenticateUser(SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getUsername(),
                            signInRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenService.generateToken(authentication);
            return new JwtAuthResponse(jwt);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Ошибка аутентификации");
        }

    }

    public JwtAuthResponse signUp(SignUpRequest signUpRequest) {
        if (userService.save(signUpRequest)) {
            return authenticateUser(new SignInRequest(signUpRequest.getUsername(),
                    signUpRequest.getPassword()));
        } else {
            throw new RuntimeException("Ошибка сохранения пользователя");
        }
    }

}
