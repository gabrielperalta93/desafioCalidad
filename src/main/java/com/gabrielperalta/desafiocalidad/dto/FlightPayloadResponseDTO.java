package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

@Data
public class FlightPayloadResponseDTO {
    private String userName;
    private double amount;
    private double interest;
    private double total;
    private FlightReservationDTO flightReservationDTO;
    private StatusCodeDTO statusCodeDTO;
}
