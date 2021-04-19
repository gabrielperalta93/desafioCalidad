package com.gabrielperalta.desafiocalidad.utils;

import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateValidator {

    public LocalDate validateDate(String dateString) throws InvalidDateException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            return LocalDate.parse(dateString.replace("/", "-"), formatter);
        }catch (Exception e){
            throw new InvalidDateException("Invalid date '" + dateString +"'. The date format must be dd/mm/yyyy.");
        }
    }

    public void CompareDates(LocalDate dateFrom, LocalDate dateTo) throws InvalidDateException {
        if (dateFrom.isAfter(dateTo))
            throw new InvalidDateException("'Date from' cannot be greater than 'date to'.");
    }
}
