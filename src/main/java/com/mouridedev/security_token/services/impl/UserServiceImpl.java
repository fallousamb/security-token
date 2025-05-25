package com.mouridedev.security_token.services.impl;

import com.mouridedev.security_token.exceptions.FonctionnelErrorCodes;
import com.mouridedev.security_token.exceptions.FonctionnelleException;
import com.mouridedev.security_token.repository.UserRepository;
import com.mouridedev.security_token.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> {
                    final FonctionnelErrorCodes error = FonctionnelErrorCodes.USER_NOT_FOUND;
                    return new FonctionnelleException(HttpStatus.SC_NOT_FOUND, error.getCode(), String.format(error.getMessage(), username));
                });
    }
}
