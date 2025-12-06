package com.ishan.service;

import com.ishan.dto.RegisterRequest;
import com.ishan.exception.BadRequestException;
import com.ishan.model.User;
import com.ishan.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_DRIVER = "ROLE_DRIVER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username is already taken!");
        }

        String role = request.getRole();
        if (role == null || (!ROLE_USER.equals(role) && !ROLE_DRIVER.equals(role))) {
            throw new BadRequestException("Invalid role. Must be ROLE_USER or ROLE_DRIVER.");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
