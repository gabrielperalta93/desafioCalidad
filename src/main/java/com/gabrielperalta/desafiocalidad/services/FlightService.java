package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.*;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface FlightService {
    public List<FlightDTO> getFlights(Map<String, String> requestParams) throws IncorrectPlaceException, InvalidDateException, ParseException, EmptySearchException;
    public FlightPayloadResponseDTO createPayload(FlightPayLoadDTO payloadDTO) throws Exception;
}
