package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelDTO {
    private int hotelId;
    private String hotelCode;
    private String name;
    private String destination;
    private String roomType;
    private int amount;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean booked;
}
