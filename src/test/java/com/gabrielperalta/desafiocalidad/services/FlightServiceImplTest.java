package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.FlightDTO;
import com.gabrielperalta.desafiocalidad.dto.FlightPayLoadDTO;
import com.gabrielperalta.desafiocalidad.dto.FlightPayloadResponseDTO;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.repositories.FlightRepositoryImpl;
import com.gabrielperalta.desafiocalidad.utils.MockCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FlightServiceImplTest {

    private FlightService flightService;

    @Mock
    private FlightRepositoryImpl flightRepository;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightService = new FlightServiceImpl(flightRepository);
        mockCreator = new MockCreator();
    }

    @Test
    @DisplayName("Get All Flights OK")
    void getFlights() throws IncorrectPlaceException, EmptySearchException, ParseException, InvalidDateException {
        List<FlightDTO> fligthsMock = mockCreator.getFlightsMock("dbFlightsTestOk.csv");
        when(flightRepository.getAllFlights()).thenReturn(fligthsMock);
        Map<String, String> requestParams = new HashMap<>();
        List<FlightDTO> fligthsReales = flightService.getFlights(requestParams);
        Assertions.assertEquals(fligthsReales, fligthsMock);
    }

    @Test
    @DisplayName("Verify empty search OK")
    void getNoFlights() throws IncorrectPlaceException, EmptySearchException, ParseException, InvalidDateException {
        List<FlightDTO> fligthsMock = mockCreator.getFlightsMock("dbFlightsTestOk.csv");
        when(flightRepository.getAllFlights()).thenReturn(fligthsMock);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dateTo", "15/02/2019");
        EmptySearchException exception = assertThrows(EmptySearchException.class, () -> flightService.getFlights(requestParams));
        assertEquals("Your search didn't find any result.", exception.getMessage());
    }

    @Test
    @DisplayName("Check flight by origin/destination filter")
    void getFlightsByOriginDestination() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
        List<FlightDTO> fligthsMock = mockCreator.getFlightsMock("dbFlightsDestinationTestOk.csv");
        when(flightRepository.getAllFlights()).thenReturn(fligthsMock);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("origin", "Tucumán");
        requestParams.put("destination", "Puerto Iguazú");
        List<FlightDTO> fligthsReales = flightService.getFlights(requestParams);
        Assertions.assertEquals(fligthsReales, fligthsMock);
    }

    @Test
    @DisplayName("Check flight by unknown destination filter")
    void getFlightsByWrongDestination() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("destination", "Buenos Airesss");
        IncorrectPlaceException exception = assertThrows(IncorrectPlaceException.class, () -> flightService.getFlights(requestParams));
        assertEquals("Destination '"+ requestParams.get("destination") +"' doesn't exists. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("Check flight by unknown origin filter")
    void getFlightsByWrongOrigin() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("origin", "Buenos Airesss");
        IncorrectPlaceException exception = assertThrows(IncorrectPlaceException.class, () -> flightService.getFlights(requestParams));
        assertEquals("Origin '"+ requestParams.get("origin") +"' doesn't exists. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("Validate date filter")
    void getFlighsWrongDate(){
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dateFrom", "10/04/21021");
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> flightService.getFlights(requestParams));
        assertEquals("Invalid date '" + requestParams.get("dateFrom") +"'. The date format must be dd/mm/yyyy.", exception.getMessage());
    }

    @Test
    @DisplayName("Validate applyFilters")
    void applyFiltersOK() throws IncorrectPlaceException, EmptySearchException, ParseException, InvalidDateException {
        List<FlightDTO> fligthsMock = mockCreator.getFlightsMock("dbFlightsFiltersTestOk.csv");
        when(flightRepository.getAllFlights()).thenReturn(fligthsMock);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dateFrom", "17/04/2021");
        requestParams.put("dateTo", "02/05/2021");
        requestParams.put("origin", "Medellín");
        requestParams.put("destination", "Puerto Iguazú");
        List<FlightDTO> fligthsReales = flightService.getFlights(requestParams);
        Assertions.assertEquals(fligthsReales, fligthsMock);
    }

    @Test
    @DisplayName("Check create payload OK")
    void createPayload() throws Exception {
        FlightPayloadResponseDTO flightPayloadResponseMock = mockCreator.createFlightPayLoadResponse();
        FlightPayLoadDTO flightPayLoadMock = mockCreator.createFlightPayLoad();

        List<FlightDTO> fligthsMock = mockCreator.getFlightsMock("dbFlightsTestOk.csv");
        when(flightRepository.getAllFlights()).thenReturn(fligthsMock);

        FlightPayloadResponseDTO flightPayloadResponseReal = flightService.createPayload(flightPayLoadMock);
        Assertions.assertEquals(flightPayloadResponseReal, flightPayloadResponseMock);
    }
}