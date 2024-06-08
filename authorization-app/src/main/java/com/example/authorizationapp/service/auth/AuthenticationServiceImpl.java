package com.example.authorizationapp.service.auth;

import com.example.authorizationapp.config.JwtConfiguration;
import com.example.authorizationapp.dto.JwtAuthenticationResponse;
import com.example.authorizationapp.dto.SignInRequest;
import com.example.authorizationapp.dto.SignUpRequest;
import com.example.authorizationapp.entity.Session;
import com.example.authorizationapp.entity.User;
import com.example.authorizationapp.repository.SessionDao;
import com.example.authorizationapp.service.jwt.JwtService;
import com.example.authorizationapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String SIGN_IN_SUCCESSFULLY = "Вход осуществлен успешно!";
    private static final String SIGN_UP_SUCCESSFULLY = "Регистрация осуществлена успешно!";

    private final JwtConfiguration jwtConfig;
    private final UserService userService;
    private final SessionDao sessionDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user = userService.create(user);

        String token = jwtService.generateToken(user);
        saveSession(token, user);

        return JwtAuthenticationResponse.builder()
                .message(SIGN_UP_SUCCESSFULLY)
                .token(token)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = (User) userService.userDetailsService().loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(user);
        saveSession(token, user);

        return JwtAuthenticationResponse.builder()
                .message(SIGN_IN_SUCCESSFULLY)
                .token(token)
                .build();
    }

    private void saveSession(String token, User user) {
        sessionDao.save(Session.builder()
                        .token(token)
                        .expires(LocalDateTime.now().plus(jwtConfig.getTokenExpiration()))
                        .userId(user.getId())
                        .build());
    }
}
