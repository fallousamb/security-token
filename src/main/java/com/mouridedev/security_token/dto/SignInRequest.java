package com.mouridedev.security_token.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
