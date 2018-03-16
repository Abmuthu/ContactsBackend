package com.muthukumaranpk.exception;

/**
 * Created by muthukumaran on 3/16/18.
 */

public class ContactNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE_TEMPLATE = "The contact %s is not found!";

    public ContactNotFoundException(String name) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, name));
    }
}
