package com.muthukumaranpk.exception;

/**
 * Created by muthukumaran on 3/16/18.
 */
public class InvalidContactFieldException extends RuntimeException {
    private static final String ERROR_MESSAGE_TEMPLATE = "Field %s is invalid!";

    public InvalidContactFieldException(String field) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, field));
    }
}
