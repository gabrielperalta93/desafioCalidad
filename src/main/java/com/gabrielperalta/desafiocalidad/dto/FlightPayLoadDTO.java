package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

@Data
public class FlightPayLoadDTO {
    private String userName;
    private FlightReservationDTO flightReservation;
}
