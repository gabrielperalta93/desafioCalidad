package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.FlightDTO;
import com.gabrielperalta.desafiocalidad.utils.MockCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightRepositoryImplTest {

    private FlightRepository flightRepository;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        flightRepository = new FlightRepositoryImpl();
        mockCreator = new MockCreator();
    }

    @Test
    @DisplayName("Check Get All Flights OK")
    void getAllFlights() {
        List<FlightDTO> flightsMock = mockCreator.getFlightsMock("dbFlightsTestOk.csv");
        List<FlightDTO> fligthsReales = flightRepository.getAllFlights();

        assertEquals(fligthsReales, flightsMock);
    }
}