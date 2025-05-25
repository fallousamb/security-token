package com.mouridedev.security_token.exceptions;

public interface ICodesErreur {

    /**
     * Return error message
     *
     * @return {@String}
     */
    String getMessage();


    /**
     * Return error code
     *
     * @return {@String}
     */
    String getCode();
}
