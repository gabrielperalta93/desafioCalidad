package com.gabrielperalta.desafiocalidad.utils;

import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidMailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    @DisplayName("Valid correct email")
    void validateCorrectEmail() throws Exception {
        String mailString = "john@gmail.com";
        emailValidator.validateEmail(mailString);
    }

    @Test
    @DisplayName("Valid wrong email")
    void validateIncorrectEmail() throws Exception {
        String mailString = "johngmail.com";
        InvalidMailException exception = assertThrows(InvalidMailException.class, () -> emailValidator.validateEmail(mailString));
        assertEquals("The email "+ mailString + " is not correct. Please try again.", exception.getMessage());
    }
}