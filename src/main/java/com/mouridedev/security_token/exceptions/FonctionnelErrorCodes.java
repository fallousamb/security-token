package com.mouridedev.security_token.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FonctionnelErrorCodes implements ICodesErreur {

    USER_NOT_FOUND("USER_NOT_FOUND_404", "User %s not found"),
    EMAIL_PASSWORD_NOT_VALID("EMAIL_PASSWORD_NOT_VALID_404", "Email or password not valid"),
    EMAIL_CONFLICT("EMAIL_CONFLICT", "Email %s already exists");

    private final String code;

    private final String message;


}
