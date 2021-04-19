package com.gabrielperalta.desafiocalidad.utils;

import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    private DateValidator dateValidator;

    @BeforeEach
    void setUp() {
        dateValidator = new DateValidator();
    }

    @Test
    @DisplayName("Check correct date format")
    void validateCorrectDate() throws InvalidDateException {
        String dateString = "10/04/2021";
        LocalDate dateToCompare = LocalDate.of(2021,04,10);
        LocalDate dateToTest = dateValidator.validateDate(dateString);
        assertEquals(dateToCompare, dateToTest);
    }

    @Test
    @DisplayName("Check incorrect date format")
    void validateIncorrectDate() throws InvalidDateException {
        String dateString = "10/04/21021";
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> dateValidator.validateDate(dateString));
        assertEquals("Invalid date '" + dateString +"'. The date format must be dd/mm/yyyy.", exception.getMessage());
    }

    @Test
    @DisplayName("Check if date from is under date to")
    void compareDatesOk() throws InvalidDateException {
        LocalDate dateFrom = LocalDate.of(2021,04,10);
        LocalDate dateTo = LocalDate.of(2021,05,10);
        dateValidator.CompareDates(dateFrom, dateTo);
    }

    @Test
    @DisplayName("Check if date from is under date to")
    void compareDatesWrong() throws InvalidDateException {
        LocalDate dateFrom = LocalDate.of(2021,06,10);
        LocalDate dateTo = LocalDate.of(2021,05,10);
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> dateValidator.CompareDates(dateFrom, dateTo));
        assertEquals("'Date from' cannot be greater than 'date to'.", exception.getMessage());
    }
}