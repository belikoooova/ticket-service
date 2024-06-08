package com.example.authorizationapp.service.user;

import com.example.authorizationapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User create(User user);

    User getByEmail(String email);

    UserDetailsService userDetailsService();

    User getCurrentUser();
}
