package com.example.authorizationapp.service.auth;

import com.example.authorizationapp.dto.JwtAuthenticationResponse;
import com.example.authorizationapp.dto.SignInRequest;
import com.example.authorizationapp.dto.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
