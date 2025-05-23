package com.mouridedev.security_token.services;

import com.mouridedev.security_token.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {

    String extractUsername(final String token);

    String generateToken(final User user);

    boolean isTokenValid(final String token, final UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, final User user);

}
