package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightDTO {
    private int flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private int amount;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
