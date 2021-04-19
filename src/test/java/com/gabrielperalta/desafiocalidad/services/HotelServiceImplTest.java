package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.*;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.exceptions.RoomCapacityException;
import com.gabrielperalta.desafiocalidad.repositories.HotelRepositoryImpl;
import com.gabrielperalta.desafiocalidad.utils.MockCreator;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class HotelServiceImplTest {

    private HotelService hotelService;

    @Mock
    private HotelRepositoryImpl hotelRepository;
    private MockCreator mockCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        hotelService = new HotelServiceImpl(hotelRepository);
        mockCreator = new MockCreator();
    }


    @Test
    @DisplayName("Get all hotels OK")
    void getHotels() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
       List<HotelDTO> hotelesMock = mockCreator.getHotelesMock("dbHotelTestOk.csv");
       when(hotelRepository.getAllHotels()).thenReturn(hotelesMock);
       Map<String, String> requestParams = new HashMap<>();
       List<HotelDTO> hotelesReales = hotelService.getHotels(requestParams);
       Assertions.assertEquals(hotelesReales, hotelesMock);
    }

    @Test
    @DisplayName("Check hotel by destination filter")
    void getHotelsByDestination() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
        List<HotelDTO> hotelesMock = mockCreator.getHotelesMock("dbHotelTestDestinationOk.csv");
        when(hotelRepository.getAllHotels()).thenReturn(hotelesMock);
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("destination", "Buenos Aires");
        List<HotelDTO> hotelesReales = hotelService.getHotels(requestParams);
        Assertions.assertEquals(hotelesReales, hotelesMock);
    }

    @Test
    @DisplayName("Check hotel by unknown destination filter")
    void getHotelsByWrongDestination() throws InvalidDateException, IncorrectPlaceException, EmptySearchException, ParseException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("destination", "Buenos Airesss");
        IncorrectPlaceException exception = assertThrows(IncorrectPlaceException.class, () -> hotelService.getHotels(requestParams));
        assertEquals("Destination '"+ requestParams.get("destination") +"' doesn't exists. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("Validate date filter")
    void getHotelWrongDate(){
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("dateFrom", "10/04/21021");
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> hotelService.getHotels(requestParams));
        assertEquals("Invalid date '" + requestParams.get("dateFrom") +"'. The date format must be dd/mm/yyyy.", exception.getMessage());
    }

    @Test
    @DisplayName("Check create payload OK")
    void createPayload() throws Exception {
        HotelPayloadResponseDTO hotelPayloadResponseMock = mockCreator.createHotelPayLoadResponse();
        HotelPayloadDTO hotelPayLoadMock = mockCreator.createHotelPayLoad();

        List<HotelDTO> hotelesMock = mockCreator.getHotelesMock("dbHotelTestOk.csv");
        when(hotelRepository.getAllHotels()).thenReturn(hotelesMock);

        HotelPayloadResponseDTO hotelPayloadResponseReal = hotelService.createPayload(hotelPayLoadMock);
        Assertions.assertEquals(hotelPayloadResponseReal, hotelPayloadResponseMock);
    }

    @Test
    @DisplayName("Verify room exception OK")
    void verifyRoomException() throws Exception {
        HotelPayloadDTO hotelPayLoadMock = mockCreator.createHotelPayLoadWrongCapacity();

        List<HotelDTO> hotelesMock = mockCreator.getHotelesMock("dbHotelTestOk.csv");
        when(hotelRepository.getAllHotels()).thenReturn(hotelesMock);

        RoomCapacityException exception = assertThrows(RoomCapacityException.class, () -> hotelService.createPayload(hotelPayLoadMock));
        assertEquals("The room capacity is 3. You can't set 2 people.", exception.getMessage());
    }
}