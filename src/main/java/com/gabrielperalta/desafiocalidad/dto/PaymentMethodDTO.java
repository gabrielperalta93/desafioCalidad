package com.gabrielperalta.desafiocalidad.dto;

import lombok.Data;

@Data
public class PaymentMethodDTO {
    private String type;
    private String number;
    private int dues;
}
