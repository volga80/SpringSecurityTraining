package com.volga.SpringSecurityTraining.controller;

import com.volga.SpringSecurityTraining.domain.JwtAuthResponse;
import com.volga.SpringSecurityTraining.dto.SignInRequest;
import com.volga.SpringSecurityTraining.dto.SignUpRequest;
import com.volga.SpringSecurityTraining.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Контроллер для регистрации и авторизации")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя", description = "Для регистрации сделайте запрос методом " +
            "POST по адресу localhost:8580/auth/sign-in. В теле запроса передайте JSON объект с username, " +
            "password, email. Метод вернет JWT.")
    @PostMapping("/sign-up")
    public JwtAuthResponse signUp(@RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя", description = "Для авторизации сделайте запрос методом " +
            "POST по адресу localhost:8580/auth/login. В теле запроса передайте JSON объект с username, password " +
            "зарегестрированного пользователя, для авторизации с ролью admin читайте readMe")
    @PostMapping("/login")
    public JwtAuthResponse login(@RequestBody SignInRequest request) {
        return authService.authenticateUser(request);
    }
}