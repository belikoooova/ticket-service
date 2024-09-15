package com.example.authorizationapp.controller;

import com.example.authorizationapp.dto.UserResponse;
import com.example.authorizationapp.entity.User;
import com.example.authorizationapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUserInfo() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(
                UserResponse.builder()
                        .email(user.getEmail())
                        .id(user.getId())
                        .password(user.getPassword())
                        .build()
        );
    }
}
