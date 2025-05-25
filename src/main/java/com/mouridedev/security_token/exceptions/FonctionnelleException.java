package com.mouridedev.security_token.exceptions;

public class FonctionnelleException extends SecurityTokenException {


    public FonctionnelleException(final int statutHttp, final String codeErreur, final String message) {
        super(statutHttp, codeErreur, message);
    }
}
