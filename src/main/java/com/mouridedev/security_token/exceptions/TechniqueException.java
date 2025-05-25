package com.mouridedev.security_token.exceptions;

public class TechniqueException extends SecurityTokenException {

    public TechniqueException(final int statutHttp, final String codeErreur, final String message) {
        super(statutHttp, codeErreur, message);
    }
}
