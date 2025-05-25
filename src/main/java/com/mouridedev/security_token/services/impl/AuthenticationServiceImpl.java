package com.mouridedev.security_token.services.impl;

import com.mouridedev.security_token.dto.JwtAuthenticationResponse;
import com.mouridedev.security_token.dto.RefreshTokenRequest;
import com.mouridedev.security_token.dto.SignInRequest;
import com.mouridedev.security_token.dto.SignUpRequest;
import com.mouridedev.security_token.entities.User;
import com.mouridedev.security_token.enums.Role;
import com.mouridedev.security_token.exceptions.FonctionnelErrorCodes;
import com.mouridedev.security_token.exceptions.FonctionnelleException;
import com.mouridedev.security_token.repository.UserRepository;
import com.mouridedev.security_token.services.IAuthenticationService;
import com.mouridedev.security_token.services.IJwtService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final IJwtService jwtService;


    @Override
    public User signUp(final SignUpRequest signUpRequest) {
        validateEmailUser(signUpRequest.getEmail());
        final User user = User.builder()
                .firstname(signUpRequest.getFirstName())
                .secondname(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signIn(final SignInRequest signinRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signinRequest.getEmail(),
                            signinRequest.getPassword()
                    )
            );
        } catch (final BadCredentialsException e) {
            final FonctionnelErrorCodes error = FonctionnelErrorCodes.EMAIL_PASSWORD_NOT_VALID;
            throw new FonctionnelleException(HttpStatus.SC_NOT_FOUND, error.getCode(), String.format(error.getMessage()));
        }
        final User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> {
                    final FonctionnelErrorCodes error = FonctionnelErrorCodes.USER_NOT_FOUND;
                    return new FonctionnelleException(HttpStatus.SC_NOT_FOUND, error.getCode(), String.format(error.getMessage(), signinRequest.getEmail()));
                }
        );

        final String token = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return buildJwtToken(token, refreshToken);
    }


    @Override
    public JwtAuthenticationResponse refreshToken(final RefreshTokenRequest refreshTokenRequest) {
        final String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        final User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            final String jwtToken = jwtService.generateToken(user);

            return buildJwtToken(jwtToken, refreshTokenRequest.getToken());

        }
        return null;
    }

    /**
     * Builds a JwtAuthenticationResponse object with the provided token and refresh token.
     *
     * @param token        Token
     * @param refreshToken Refresh token
     * @return JwtAuthenticationResponse
     */
    private static JwtAuthenticationResponse buildJwtToken(final String token, final String refreshToken) {
        final JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }


    /**
     * Validates if the provided email is already used by another user.
     *
     * @param email the email to check
     */
    private void validateEmailUser(final String email) {
        final Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            final FonctionnelErrorCodes error = FonctionnelErrorCodes.EMAIL_CONFLICT;
            throw new FonctionnelleException(HttpStatus.SC_CONFLICT, error.getCode(), String.format(error.getMessage(), email));
        }
    }
}
