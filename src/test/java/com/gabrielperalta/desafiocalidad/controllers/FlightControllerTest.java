package com.gabrielperalta.desafiocalidad.controllers;

import com.gabrielperalta.desafiocalidad.dto.FlightDTO;
import com.gabrielperalta.desafiocalidad.dto.FlightPayLoadDTO;
import com.gabrielperalta.desafiocalidad.dto.FlightPayloadResponseDTO;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.services.FlightServiceImpl;
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

class FlightControllerTest {

    @Mock
    FlightServiceImpl flightService;
    private FlightController flightController;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightController = new FlightController(flightService);
        mockCreator = new MockCreator();
    }

    @Test
    @DisplayName("Check Get All Flights OK")
    void getAll_OK() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {

        List<FlightDTO> lista = mockCreator.getFlightsMock("dbFlightsTestOk.csv");

        Map<String, String> requestParams = new HashMap<>();
        when(flightService.getFlights(requestParams)).thenReturn(lista);

        ResponseEntity responseEntity = (ResponseEntity) flightController.getFlights(requestParams);
        List<FlightDTO> listaAux = (List<FlightDTO>) responseEntity.getBody();

        assertEquals(listaAux, lista);
    }

    @Test
    @DisplayName("Check create payload OK")
    void createPayLoad() throws Exception {
        FlightPayloadResponseDTO flightPayloadResponseMock = mockCreator.createFlightPayLoadResponse();
        when(flightService.createPayload(any())).thenReturn(flightPayloadResponseMock);

        FlightPayLoadDTO flightPayLoadDTO = mockCreator.createFlightPayLoad();
        ResponseEntity responseEntity = (ResponseEntity) flightController.createPayLoad(flightPayLoadDTO);
        FlightPayloadResponseDTO flightResponseReal = (FlightPayloadResponseDTO) responseEntity.getBody();

        assertEquals(flightResponseReal, flightPayloadResponseMock);
    }
}