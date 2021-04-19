package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.FlightDTO;

import java.util.List;
import java.util.Map;

public interface FlightRepository {
    public List<FlightDTO> getAllFlights();
}
