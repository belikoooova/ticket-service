package com.example.authorizationapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private static final String NAME_NOT_BLANK = "Имя пользователя не может быть пустым";
    private static final String EMAIL_NOT_BLANK = "Email адрес не может быть пустым";
    private static final String EMAIL_INVALID = "Email адрес должен быть в формате user@example.com";
    private static final String PASSWORD_NOT_BLANK = "Email адрес не может быть пустым";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[.,?@#!$%^&+=])(?=\\S+$).{8,}$";
    private static final String PASSWORD_INVALID = "Пароль должен состоять из не менее восьми символов, включая буквы обоих регистров, цифры и специальные символы";

    @NotBlank(message = EMAIL_NOT_BLANK)
    @Email(message = EMAIL_INVALID)
    private String email;

    @NotBlank(message = NAME_NOT_BLANK)
    private String nickname;

    @NotBlank(message = PASSWORD_NOT_BLANK)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_INVALID)
    private String password;
}
