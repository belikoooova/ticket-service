package com.example.authorizationapp.service.user;

import com.example.authorizationapp.exception.UserWithSuchEmailAlreadyExistsException;
import com.example.authorizationapp.repository.UserDao;
import com.example.authorizationapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional
    public User create(User user) {
        if (userDao.existsByEmail(user.getEmail())) {
            throw new UserWithSuchEmailAlreadyExistsException();
        }

        return userDao.save(user);
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    @Override
    @Transactional
    public User getCurrentUser() {
        return getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
