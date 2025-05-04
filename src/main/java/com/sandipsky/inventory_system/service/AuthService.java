package com.sandipsky.inventory_system.service;

import com.sandipsky.inventory_system.repository.*;
import com.sandipsky.inventory_system.entity.*;
import com.sandipsky.inventory_system.dto.login.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return userRepository.findByUsername(request.getUsername())
                .orElseThrow();
    }
}
