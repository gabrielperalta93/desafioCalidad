package com.gabrielperalta.desafiocalidad.utils;

import com.gabrielperalta.desafiocalidad.dto.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockCreator {

    public List<HotelDTO> getHotelesMock(String fileName){
        List<HotelDTO> hotels = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try
        {
            String filePath = new File("").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(filePath + "/src/main/resources/" + fileName));
            Boolean first = true;
            while ((line = br.readLine()) != null)
            {
                if (!first){
                    String[] hoteles = line.split(splitBy);
                    HotelDTO hotelDTO = new HotelDTO();
                    hotelDTO.setHotelId(Integer.parseInt(hoteles[0]));
                    hotelDTO.setHotelCode(hoteles[1]);
                    hotelDTO.setName((hoteles[2]));
                    hotelDTO.setDestination((hoteles[3]));
                    hotelDTO.setRoomType((hoteles[4]));
                    int precioNoche = Integer.parseInt(hoteles[5].replace("$", ""));
                    hotelDTO.setAmount(precioNoche);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(hoteles[6].replace("/", "-"), formatter);
                    hotelDTO.setDateFrom((date));
                    date = LocalDate.parse(hoteles[7].replace("/", "-"), formatter);
                    hotelDTO.setDateTo((date));
                    Boolean reservado = false;
                    if (hoteles[8].toLowerCase().equals("si")){
                        reservado = true;
                    }
                    hotelDTO.setBooked(reservado);
                    hotels.add(hotelDTO);
                }else
                    first = false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return hotels;
    }

    public List<FlightDTO> getFlightsMock(String fileName){
        List<FlightDTO> flightDTOS = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try
        {
            String filePath = new File("").getAbsolutePath();
            BufferedReader br = new BufferedReader(new FileReader(filePath + "/src/main/resources/" + fileName));
            Boolean first = true;
            while ((line = br.readLine()) != null)
            {
                if (!first){
                    String[] flights = line.split(splitBy);
                    FlightDTO flightDTO = new FlightDTO();
                    flightDTO.setFlightId(Integer.parseInt(flights[0]));
                    flightDTO.setFlightNumber(flights[1]);
                    flightDTO.setOrigin((flights[2]));
                    flightDTO.setDestination((flights[3]));
                    flightDTO.setSeatType((flights[4]));
                    flightDTO.setAmount(Integer.parseInt(flights[5].replace("$", "").replace(".", "")));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(flights[6].replace("/", "-"), formatter);
                    flightDTO.setDateFrom((date));
                    date = LocalDate.parse(flights[7].replace("/", "-"), formatter);
                    flightDTO.setDateTo((date));
                    flightDTOS.add(flightDTO);
                }else
                    first = false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return flightDTOS;
    }

    public FlightPayloadResponseDTO createFlightPayLoadResponse(){
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO();
        people1.setDni("12345678");
        people1.setName("Pepe");
        people1.setLastName("Gomez");
        people1.setBirthDate("02/12/1988");
        people1.setMail("pepe@gmail.com");

        PeopleDTO people2 = new PeopleDTO();
        people2.setDni("13345678");
        people2.setName("Fulanito");
        people2.setLastName("Gomez");
        people2.setBirthDate("15/12/1983");
        people2.setMail("fulanito@gmail.com");

        people.add(people1);
        people.add(people2);

        FlightReservationDTO flightReservationDTO = new FlightReservationDTO();
        flightReservationDTO.setDateFrom("10/11/2021");
        flightReservationDTO.setDateTo("20/11/2021");
        flightReservationDTO.setOrigin("Buenos Aires");
        flightReservationDTO.setDestination("Puerto Iguazú");
        flightReservationDTO.setFlightNumber("BAPI-1235");
        flightReservationDTO.setSeats(2);
        flightReservationDTO.setSeatType("ECONOMY");
        flightReservationDTO.setPeople(people);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues(6);
        flightReservationDTO.setPaymentMethod(paymentMethod);

        FlightPayloadResponseDTO flightPayloadResponseDTO = new FlightPayloadResponseDTO();
        flightPayloadResponseDTO.setUserName("pepe@gmail.com");
        flightPayloadResponseDTO.setAmount(13000.0);
        flightPayloadResponseDTO.setInterest(1300.0);
        flightPayloadResponseDTO.setTotal(14300.000000000002);

        StatusCodeDTO statusCodeDTO = new StatusCodeDTO();
        statusCodeDTO.setCode(200);
        statusCodeDTO.setMessage("The process ended succesfully!");
        flightPayloadResponseDTO.setStatusCodeDTO(statusCodeDTO);
        flightPayloadResponseDTO.setFlightReservationDTO(flightReservationDTO);
        return flightPayloadResponseDTO;
    }

    public FlightPayLoadDTO createFlightPayLoad(){
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO();
        people1.setDni("12345678");
        people1.setName("Pepe");
        people1.setLastName("Gomez");
        people1.setBirthDate("02/12/1988");
        people1.setMail("pepe@gmail.com");

        PeopleDTO people2 = new PeopleDTO();
        people2.setDni("13345678");
        people2.setName("Fulanito");
        people2.setLastName("Gomez");
        people2.setBirthDate("15/12/1983");
        people2.setMail("fulanito@gmail.com");

        people.add(people1);
        people.add(people2);

        FlightReservationDTO flightReservationDTO = new FlightReservationDTO();
        flightReservationDTO.setDateFrom("10/11/2021");
        flightReservationDTO.setDateTo("20/11/2021");
        flightReservationDTO.setOrigin("Buenos Aires");
        flightReservationDTO.setDestination("Puerto Iguazú");
        flightReservationDTO.setFlightNumber("BAPI-1235");
        flightReservationDTO.setSeats(2);
        flightReservationDTO.setSeatType("ECONOMY");
        flightReservationDTO.setPeople(people);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues(6);
        flightReservationDTO.setPaymentMethod(paymentMethod);

        FlightPayLoadDTO flightPayLoadDTO = new FlightPayLoadDTO();
        flightPayLoadDTO.setUserName("pepe@gmail.com");
        flightPayLoadDTO.setFlightReservation(flightReservationDTO);
        return flightPayLoadDTO;
    }

    public HotelPayloadDTO createHotelPayLoadWrongCapacity(){
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO();
        people1.setDni("12345678");
        people1.setName("Pepe");
        people1.setLastName("Gomez");
        people1.setBirthDate("02/12/1988");
        people1.setMail("pepe@gmail.com");

        PeopleDTO people2 = new PeopleDTO();
        people2.setDni("13345678");
        people2.setName("Fulanito");
        people2.setLastName("Gomez");
        people2.setBirthDate("15/12/1983");
        people2.setMail("fulanito@gmail.com");

        people.add(people1);
        people.add(people2);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setDateFrom("10/11/2021");
        bookingDTO.setDateTo("20/11/2021");
        bookingDTO.setDestination("Puerto Iguazú");
        bookingDTO.setHotelCode("CH-0002");
        bookingDTO.setPeopleAmount(2);
        bookingDTO.setRoomType("triple");
        bookingDTO.setPeople(people);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues(6);
        bookingDTO.setPaymentMethod(paymentMethod);

        HotelPayloadDTO hotelPayloadDTO = new HotelPayloadDTO();
        hotelPayloadDTO.setUserName("pepe@gmail.com");
        hotelPayloadDTO.setBooking(bookingDTO);
        return hotelPayloadDTO;
    }

    public HotelPayloadDTO createHotelPayLoad(){
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO();
        people1.setDni("12345678");
        people1.setName("Pepe");
        people1.setLastName("Gomez");
        people1.setBirthDate("02/12/1988");
        people1.setMail("pepe@gmail.com");

        PeopleDTO people2 = new PeopleDTO();
        people2.setDni("13345678");
        people2.setName("Fulanito");
        people2.setLastName("Gomez");
        people2.setBirthDate("15/12/1983");
        people2.setMail("fulanito@gmail.com");

        people.add(people1);
        people.add(people2);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setDateFrom("10/11/2021");
        bookingDTO.setDateTo("20/11/2021");
        bookingDTO.setDestination("Puerto Iguazú");
        bookingDTO.setHotelCode("CH-0002");
        bookingDTO.setPeopleAmount(2);
        bookingDTO.setRoomType("double");
        bookingDTO.setPeople(people);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues(6);
        bookingDTO.setPaymentMethod(paymentMethod);

        HotelPayloadDTO hotelPayloadDTO = new HotelPayloadDTO();
        hotelPayloadDTO.setUserName("pepe@gmail.com");
        hotelPayloadDTO.setBooking(bookingDTO);
        return hotelPayloadDTO;
    }

    public HotelPayloadResponseDTO createHotelPayLoadResponse(){
        List<PeopleDTO> people = new ArrayList<>();
        PeopleDTO people1 = new PeopleDTO();
        people1.setDni("12345678");
        people1.setName("Pepe");
        people1.setLastName("Gomez");
        people1.setBirthDate("02/12/1988");
        people1.setMail("pepe@gmail.com");

        PeopleDTO people2 = new PeopleDTO();
        people2.setDni("13345678");
        people2.setName("Fulanito");
        people2.setLastName("Gomez");
        people2.setBirthDate("15/12/1983");
        people2.setMail("fulanito@gmail.com");

        people.add(people1);
        people.add(people2);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setDateFrom("10/11/2021");
        bookingDTO.setDateTo("20/11/2021");
        bookingDTO.setDestination("Puerto Iguazú");
        bookingDTO.setHotelCode("CH-0002");
        bookingDTO.setPeopleAmount(2);
        bookingDTO.setRoomType("double");
        bookingDTO.setPeople(people);
        PaymentMethodDTO paymentMethod = new PaymentMethodDTO();
        paymentMethod.setType("CREDIT");
        paymentMethod.setNumber("1234-1234-1234-1234");
        paymentMethod.setDues(6);
        bookingDTO.setPaymentMethod(paymentMethod);

        HotelPayloadResponseDTO hotelPayloadResponseDTO = new HotelPayloadResponseDTO();
        hotelPayloadResponseDTO.setUserName("pepe@gmail.com");
        hotelPayloadResponseDTO.setAmount(63000.0);
        hotelPayloadResponseDTO.setInterest(6300.0);
        hotelPayloadResponseDTO.setTotal(69300.0);

        StatusCodeDTO statusCodeDTO = new StatusCodeDTO();
        statusCodeDTO.setCode(200);
        statusCodeDTO.setMessage("The process ended succesfully!");
        hotelPayloadResponseDTO.setStatusCodeDTO(statusCodeDTO);
        hotelPayloadResponseDTO.setBookingDTO(bookingDTO);
        return hotelPayloadResponseDTO;
    }
}
