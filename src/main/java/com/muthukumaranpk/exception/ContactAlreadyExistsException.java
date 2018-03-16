package com.muthukumaranpk.exception;

/**
 * Created by muthukumaran on 3/16/18.
 */
public class ContactAlreadyExistsException extends RuntimeException {
    private static final String ERROR_MESSAGE_TEMPLATE = "The contact %s already exists!";

    public ContactAlreadyExistsException(String name) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, name));
    }
}
