package com.volga.SpringSecurityTraining.controller;

import com.volga.SpringSecurityTraining.dto.UserDTO;
import com.volga.SpringSecurityTraining.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользовательский контроллер", description = "Контроллер тестовых запросов к данным " +
        "у пользователей с разными ролями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Данные текущего пользователя", description = "Любой авторизованный пользователь " +
            "может увидеть свои данные вызвав этот метод")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/myname")
    public ResponseEntity<String> getName() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    @Operation(summary = "Список всех пользователей", description = "Только пользователь с ролью 'ADMIN' " +
            "может увидеть список всех зарегистрированных пользователей")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Приветствие", description = "Любой пользователь может увидеть приветствие")
    @GetMapping("/text")
    public ResponseEntity<String> getSimpleText() {
        return ResponseEntity.ok("Приветствие");
    }
}
