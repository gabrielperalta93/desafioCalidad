package com.gabrielperalta.desafiocalidad.repositories;

import com.gabrielperalta.desafiocalidad.dto.HotelDTO;
import com.gabrielperalta.desafiocalidad.utils.MockCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class HotelRepositoryImplTest {

    private HotelRepository hotelRepository;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        hotelRepository = new HotelRepositoryImpl();
        mockCreator = new MockCreator();
    }

    @Test
    @DisplayName("Check Get All Hotels OK")
    void getAllHotelsEquals() {
        List<HotelDTO> hotelesMock = mockCreator.getHotelesMock("dbHotelTestOk.csv");
        List<HotelDTO> hotelesReales = hotelRepository.getAllHotels();

        assertEquals(hotelesReales, hotelesMock);
    }

}