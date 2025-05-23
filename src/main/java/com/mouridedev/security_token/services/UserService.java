package com.mouridedev.security_token.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    /**
     * Return the implementation of UserDetailsService.
     *
     * @return an instance of UserDetailsService
     */
    UserDetailsService userDetailsService();
}
