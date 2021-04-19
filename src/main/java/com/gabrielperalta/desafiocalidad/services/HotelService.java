package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.HotelDTO;
import com.gabrielperalta.desafiocalidad.dto.HotelPayloadDTO;
import com.gabrielperalta.desafiocalidad.dto.HotelPayloadResponseDTO;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface HotelService {
    public List<HotelDTO> getHotels(Map<String, String> requestParams) throws ParseException, InvalidDateException, EmptySearchException, IncorrectPlaceException;
    public HotelPayloadResponseDTO createPayload(HotelPayloadDTO payloadDTO) throws Exception;
}
