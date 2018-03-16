package com.muthukumaranpk.util;

import com.muthukumaranpk.entity.Contact;

/**
 * Created by muthukumaran on 3/16/18.
 */
public class Validator {

    public static void validateContact(Contact contact) {
        validateName(contact.getName());
        validatePhoneNumber(contact.getPhoneNumber());
        validateAddress(contact.getAddress());
        validateEmail(contact.getEmail());
    }

    private static void validateName(String name) {
        // if invalid throw exception
    }

    private static void validatePhoneNumber(long phoneNumber) {
        // if invalid throw exception
    }

    private static void validateAddress(String address) {
        // if invalid throw exception
    }

    private static void validateEmail(String email) {
        // if invalid throw exception
    }

}
