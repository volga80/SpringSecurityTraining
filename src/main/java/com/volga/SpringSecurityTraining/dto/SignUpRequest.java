package com.volga.SpringSecurityTraining.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя")
    private String username;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Почта")
    private String email;
}
