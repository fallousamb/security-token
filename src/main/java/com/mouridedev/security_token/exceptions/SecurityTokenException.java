package com.mouridedev.security_token.exceptions;

import lombok.Getter;

@Getter
public abstract class SecurityTokenException extends RuntimeException {

    private final int statutHttp;

    private final String codeErreur;

    protected SecurityTokenException(final int statutHttp, final String codeErreur, final String message) {
        super(message);
        this.statutHttp = statutHttp;
        this.codeErreur = codeErreur;
    }
}
