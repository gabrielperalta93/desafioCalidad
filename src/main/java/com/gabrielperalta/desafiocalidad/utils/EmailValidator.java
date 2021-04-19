package com.gabrielperalta.desafiocalidad.utils;

import com.gabrielperalta.desafiocalidad.exceptions.InvalidMailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validateEmail(String emailStr) throws InvalidMailException {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        if (!matcher.find())
            throw new InvalidMailException("The email " + emailStr + " is not correct. Please try again.");
    }
}
