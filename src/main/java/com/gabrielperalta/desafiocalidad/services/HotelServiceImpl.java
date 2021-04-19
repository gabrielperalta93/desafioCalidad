package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.*;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.exceptions.RoomCapacityException;
import com.gabrielperalta.desafiocalidad.repositories.HotelRepository;
import com.gabrielperalta.desafiocalidad.utils.DateValidator;
import com.gabrielperalta.desafiocalidad.utils.EmailValidator;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService{
    private HashMap<String, String> hotelsFilter;
    private EmailValidator emailValidator = new EmailValidator();
    private DateValidator dateValidator = new DateValidator();

    private HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelDTO> getHotels(Map<String, String> requestParams) throws ParseException, InvalidDateException, EmptySearchException, IncorrectPlaceException {
        List<HotelDTO> hoteles = hotelRepository.getAllHotels();
        if (requestParams.isEmpty())
            return hoteles.stream().filter(a -> a.getBooked().equals(false)).collect(Collectors.toList());;

        String dateFrom = requestParams.get("dateFrom");
        String dateTo = requestParams.get("dateTo");
        String destination = requestParams.get("destination");
        hotelsFilter = new HashMap<>();
        if(dateFrom != null)
            hotelsFilter.put("dateFrom", dateFrom);
        if (dateTo != null)
            hotelsFilter.put("dateTo", dateTo);
        if (destination != null){
            validateDestination(destination);
            hotelsFilter.put("destination", destination);
        }

        if (dateFrom != null && dateTo != null){
            LocalDate from = dateValidator.validateDate(dateFrom);
            LocalDate to = dateValidator.validateDate(dateTo);
            dateValidator.CompareDates(from, to);
        }

        hoteles = applyFilters(hoteles);

        if(hoteles.size() == 0)
            throw new EmptySearchException();

        return hoteles;
    }

    @Override
    public HotelPayloadResponseDTO createPayload(HotelPayloadDTO payloadDTO) throws Exception {
        LocalDate from = dateValidator.validateDate(payloadDTO.getBooking().getDateFrom()); //Validate Date From
        LocalDate to = dateValidator.validateDate(payloadDTO.getBooking().getDateTo()); //Validate Date To
        dateValidator.CompareDates(from, to);
        validateDestination(payloadDTO.getBooking().getDestination());
        int typeRoom = 0;
        switch (payloadDTO.getBooking().getRoomType().toLowerCase()) {
            case "double":
                typeRoom = 2;
                break;
            case "triple":
                typeRoom = 3;
                break;
            case "múltiple":
                typeRoom = 4;
                break;
            default:
                throw new Exception("Wrong room type selected. The values are 'Double', 'Triple' or 'Múltiple'");
        }

        if (payloadDTO.getBooking().getPeople().size() != typeRoom)
            throw new RoomCapacityException("The room capacity is " + typeRoom + ". You can't set " + payloadDTO.getBooking().getPeople().size() + " people.");

        validateDestination(payloadDTO.getBooking().getDestination());

        emailValidator.validateEmail(payloadDTO.getUserName());

        for (PeopleDTO people : payloadDTO.getBooking().getPeople()) {
            emailValidator.validateEmail(people.getMail());
        }

        double amount = 0;
        double total = 0;
        double interest = 0;
        int cantidadDias = (int) Duration.between(from.atStartOfDay(), to.atStartOfDay()).toDays();
        int precioPorNoche = 0;
        List<HotelDTO> hoteles = hotelRepository.getAllHotels();
        HotelDTO modifidedHotel = new HotelDTO();
        for (HotelDTO hotel : hoteles) {
            if (hotel.getHotelCode().equals(payloadDTO.getBooking().getHotelCode())){
                if (hotel.getBooked())
                    throw new Exception("The hotel with code '" + hotel.getHotelCode() + "' is already booked.");

                precioPorNoche = hotel.getAmount();
                modifidedHotel = hotel;
                break;
            }
        }
        if (precioPorNoche == 0)
            throw new Exception("The hotel code '" + payloadDTO.getBooking().getHotelCode() + "' doesn't exists.");

        amount = cantidadDias * precioPorNoche;
        if (payloadDTO.getBooking().getPaymentMethod().getType().toLowerCase().equals("credit")){
            int cuotas = payloadDTO.getBooking().getPaymentMethod().getDues();
            if (cuotas > 1 && cuotas <= 3){
                interest = amount * 0.05;
                total = amount * 1.05;
            }
            else if (cuotas > 3 && cuotas <= 6){
                interest = amount * 0.1;
                total = amount * 1.1;
            }
            else if (cuotas > 6 && cuotas <= 12){
                interest = amount * 0.15;
                total = amount * 1.15;
            }
            else
                throw new Exception("Dues must be between 1 and 12.");
        }else if(payloadDTO.getBooking().getPaymentMethod().getType().toLowerCase().equals("debit")){
            if (payloadDTO.getBooking().getPaymentMethod().getDues() != 1)
                throw new Exception("Dues must be 1 when payment method is debit card.");
            total = amount;
        }else
            throw new Exception("Payment method must be 'credit' or 'debit'.");

        for (HotelDTO hotel : hoteles) {
            if (hotel == modifidedHotel){
                modifidedHotel.setBooked(true);
                hotel = modifidedHotel;
            }
        }
        hotelRepository.updateBoookedHotels(hoteles);

        HotelPayloadResponseDTO payloadResponse = new HotelPayloadResponseDTO();
        payloadResponse.setUserName(payloadDTO.getUserName());
        payloadResponse.setAmount(amount);
        payloadResponse.setInterest(interest);
        payloadResponse.setTotal(total);
        payloadResponse.setBookingDTO(payloadDTO.getBooking());
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO();
        statusCodeDTO.setCode(200);
        statusCodeDTO.setMessage("The process ended succesfully!");
        payloadResponse.setStatusCodeDTO(statusCodeDTO);
        return payloadResponse;
    }

    public List<HotelDTO> applyFilters(List<HotelDTO> hoteles) throws ParseException, InvalidDateException {
        if(hotelsFilter.containsKey("dateFrom")) {
            LocalDate dateFrom = dateValidator.validateDate(hotelsFilter.get("dateFrom")).plusDays(-1);
            hoteles = hoteles.stream().filter(a -> a.getDateFrom().isAfter((dateFrom))).collect(Collectors.toList());
        }
        if(hotelsFilter.containsKey("dateTo")) {
            LocalDate dateTo = dateValidator.validateDate(hotelsFilter.get("dateTo")).plusDays(1);
            hoteles = hoteles.stream().filter(a -> a.getDateTo().isBefore((dateTo))).collect(Collectors.toList());
        }
        if(hotelsFilter.containsKey("destination")) {
            hoteles = hoteles.stream().filter(a -> a.getDestination().toLowerCase().equals(hotelsFilter.get("destination").toLowerCase())).collect(Collectors.toList());
        }
        hoteles = hoteles.stream().filter(a -> a.getBooked().equals(false)).collect(Collectors.toList());
        return hoteles;
    }

    public void validateDestination(String destination) throws IncorrectPlaceException {
        List<HotelDTO> hoteles = hotelRepository.getAllHotels();
        Boolean destinationExists = false;
        for (HotelDTO hotel : hoteles) {
            if (hotel.getDestination().equals(destination)){
                destinationExists = true;
                break;
            }
        }
        if (!destinationExists)
            throw new IncorrectPlaceException("Destination '"+ destination +"' doesn't exists. Please try again.");
    }
}
