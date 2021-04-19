package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

@Data
public class HotelPayloadResponseDTO {
    private String userName;
    private double amount;
    private double interest;
    private double total;
    private BookingDTO bookingDTO;
    private StatusCodeDTO statusCodeDTO;
}
