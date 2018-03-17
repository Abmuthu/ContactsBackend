package com.muthukumaranpk.util;

import com.muthukumaranpk.entity.Contact;
import com.muthukumaranpk.exception.InvalidContactFieldException;
import org.apache.commons.validator.routines.EmailValidator;

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
        if (name == null || name.length() < 1 || name.length() > 50) {
            throw new InvalidContactFieldException("name");
        }
    }

    private static void validatePhoneNumber(long phoneNumber) {
        int noOfDigits = getNoOfDigits(phoneNumber);
        if (phoneNumber <= 0L || noOfDigits != 10) {
            throw new InvalidContactFieldException("phoneNumber");
        }
    }

    private static void validateAddress(String address) {
        if (address ==  null || address.length() < 1 || address.length() > 100) {
            throw new InvalidContactFieldException("address");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 50) {
            throw new InvalidContactFieldException("email");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new InvalidContactFieldException("email");
        }
    }

    private static int getNoOfDigits(long num) {
        int count = 0;
        while(num != 0)
        {
            num /= 10;
            ++count;
        }
        return count;
    }

}
