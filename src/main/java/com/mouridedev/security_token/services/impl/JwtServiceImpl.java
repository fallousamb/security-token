package com.mouridedev.security_token.services.impl;

import com.mouridedev.security_token.entities.User;
import com.mouridedev.security_token.repository.UserRepository;
import com.mouridedev.security_token.services.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements IJwtService {


    private final UserRepository userRepository;
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration}")
    private int expiration;

    @Value("${jwt.token.refresh-expiration}")
    private int refreshExpiration;


    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token from which the username is to be extracted
     * @return the username extracted from the JWT token
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the provided JWT token by checking if the token's username matches
     * the given UserDetails' username and ensures the token has not expired.
     *
     * @param token       the JWT token to be validated
     * @param userDetails the UserDetails containing the expected username
     * @return true if the token is valid and not expired; false otherwise
     */
    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Generates a JWT token containing the user's subject (username) and a timestamp
     * representing the token's expiration date.
     *
     * @param userDetails the user that the token is being created for
     * @return a JWT token in compacted form
     */
    public String generateToken(final User user) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public String generateRefreshToken(final Map<String, Object> claims, final User user) {
        claims.put("role", user.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Retrieves the signing key used for JWT token creation.
     * The key is derived from a secret key encoded in Base64.
     *
     * @return the signing key as a Key object
     */
    private Key getSigninKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts a specific claim from the provided JWT token using a claims resolver function.
     *
     * @param token          the JWT token from which the claim is to be extracted
     * @param claimsResolver a function to extract the desired claim from the claims
     * @param <T>            the type of the claim to be extracted
     * @return the extracted claim of type T
     */
    private <T> T extractClaim(final String token,
                               final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token from which claims are to be extracted
     * @return the claims contained within the JWT token
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Checks if the provided JWT token has expired.
     *
     * @param token the JWT token to be checked for expiration
     * @return true if the token has expired; false otherwise
     */
    private boolean isTokenExpired(final String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
