package com.mouridedev.security_token.services;

import com.mouridedev.security_token.dto.JwtAuthenticationResponse;
import com.mouridedev.security_token.dto.RefreshTokenRequest;
import com.mouridedev.security_token.dto.SignInRequest;
import com.mouridedev.security_token.dto.SignUpRequest;
import com.mouridedev.security_token.entities.User;

public interface IAuthenticationService {

    /**
     * Creates a new user.
     *
     * @param signUpRequest the user's credentials
     * @return the created user
     */
    User signUp(final SignUpRequest signUpRequest);


    /**
     * Signs in a user and returns a JWT authentication response.
     *
     * @param signinRequest the user's credentials
     * @return a JWT authentication response
     */
    JwtAuthenticationResponse signIn(final SignInRequest signinRequest);

    JwtAuthenticationResponse refreshToken(final RefreshTokenRequest refreshTokenRequest);
}
