package com.example.orderapp.auth;

import com.example.orderapp.exception.InvalidAuthorizationException;
import com.example.orderapp.exception.InvalidRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class AuthorizationClient {
    private static final String AUTH_PATH = "/users/me";

    private final WebClient webClient;
    private final Duration timeout;

    public AuthorizationClient(WebClient.Builder builder, AuthorizationClientConfiguration config) {
        this.webClient = builder.baseUrl(config.getBaseUrl()).build();
        this.timeout = config.getTimeout();
    }

    public User authentificate(String bearerHeader) {
        return webClient.get()
                .uri(AUTH_PATH)
                .header(HttpHeaders.AUTHORIZATION, bearerHeader)
                .retrieve()
                .onStatus(
                        status -> HttpStatus.UNAUTHORIZED.equals(status) || HttpStatus.FORBIDDEN.equals(status),
                        response -> response.bodyToMono(InvalidAuthorizationException.class)
                )
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(InvalidRequestException.class)
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(InvalidAuthorizationException.class)
                )
                .bodyToMono(User.class)
                .block(timeout);
    }
}
