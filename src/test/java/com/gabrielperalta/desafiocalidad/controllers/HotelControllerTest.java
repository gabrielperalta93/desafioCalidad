package com.gabrielperalta.desafiocalidad.controllers;

import com.gabrielperalta.desafiocalidad.dto.*;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.services.HotelServiceImpl;
import com.gabrielperalta.desafiocalidad.utils.MockCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HotelControllerTest {

    @Mock
    HotelServiceImpl hotelService;
    private HotelController hotelController;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotelController = new HotelController(hotelService);
        mockCreator = new MockCreator();
    }

    @Test
    @DisplayName("Check Get All Hotels OK")
    void getAll_OK() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {

        List<HotelDTO> lista = mockCreator.getHotelesMock("dbHotelTestOk.csv");

        Map<String, String> requestParams = new HashMap<>();
        when(hotelService.getHotels(requestParams)).thenReturn(lista);

        ResponseEntity responseEntity = (ResponseEntity) hotelController.getHotels(requestParams);
        List<HotelDTO> listaAux = (List<HotelDTO>) responseEntity.getBody();

        assertEquals(listaAux, lista);
    }

    @Test
    @DisplayName("Check create payload OK")
    void createPayLoad() throws Exception {
        HotelPayloadResponseDTO hotelPayLoadResponseMock = mockCreator.createHotelPayLoadResponse();
        when(hotelService.createPayload(any())).thenReturn(hotelPayLoadResponseMock);

        HotelPayloadDTO hotelPayloadDTO = mockCreator.createHotelPayLoad();
        ResponseEntity responseEntity = (ResponseEntity) hotelController.createPayLoad(hotelPayloadDTO);
        HotelPayloadResponseDTO hotelResponseReal = (HotelPayloadResponseDTO) responseEntity.getBody();

        assertEquals(hotelResponseReal, hotelPayLoadResponseMock);
    }

}