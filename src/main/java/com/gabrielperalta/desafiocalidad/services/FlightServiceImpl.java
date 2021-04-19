package com.gabrielperalta.desafiocalidad.services;

import com.gabrielperalta.desafiocalidad.dto.*;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.repositories.FlightRepository;
import com.gabrielperalta.desafiocalidad.utils.DateValidator;
import com.gabrielperalta.desafiocalidad.utils.EmailValidator;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService{
    private HashMap<String, String> flightsFilter;
    private EmailValidator emailValidator = new EmailValidator();
    private DateValidator dateValidator = new DateValidator();

    FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightDTO> getFlights(Map<String, String> requestParams) throws IncorrectPlaceException, InvalidDateException, ParseException, EmptySearchException {
        List<FlightDTO> fligths = flightRepository.getAllFlights();
        if (requestParams.isEmpty())
            return fligths;

        String dateFrom = requestParams.get("dateFrom");
        String dateTo = requestParams.get("dateTo");
        String origin = requestParams.get("origin");
        String destination = requestParams.get("destination");
        flightsFilter = new HashMap<>();
        if(dateFrom != null)
            flightsFilter.put("dateFrom", dateFrom);
        if (dateTo != null)
            flightsFilter.put("dateTo", dateTo);
        if (origin != null){
            validateOrigin(origin);
            flightsFilter.put("origin", origin);
        }
        if (destination != null){
            validateDestination(destination);
            flightsFilter.put("destination", destination);
        }

        if (dateFrom != null && dateTo != null){
            LocalDate from = dateValidator.validateDate(dateFrom);
            LocalDate to = dateValidator.validateDate(dateTo);
            dateValidator.CompareDates(from, to);
        }

        fligths = applyFilters(fligths);

        if(fligths.size() == 0)
            throw new EmptySearchException();

        return fligths;

    }

    @Override
    public FlightPayloadResponseDTO createPayload(FlightPayLoadDTO payloadDTO) throws Exception {
        List<FlightDTO> flights = flightRepository.getAllFlights();
        LocalDate from = dateValidator.validateDate(payloadDTO.getFlightReservation().getDateFrom()); //Validate Date From
        LocalDate to = dateValidator.validateDate(payloadDTO.getFlightReservation().getDateTo()); //Validate Date To
        dateValidator.CompareDates(from, to);
        validateDestination(payloadDTO.getFlightReservation().getOrigin());
        validateDestination(payloadDTO.getFlightReservation().getDestination());
        emailValidator.validateEmail(payloadDTO.getUserName());

        for (PeopleDTO people : payloadDTO.getFlightReservation().getPeople()) {
            emailValidator.validateEmail(people.getMail());
        }

        double amount = 0;
        double total = 0;
        double interest = 0;
        int cantidadPersonas = payloadDTO.getFlightReservation().getPeople().size();
        int precioPorPersona = 0;

        for (FlightDTO flight : flights) {
            if (flight.getFlightNumber().equals(payloadDTO.getFlightReservation().getFlightNumber())){
                precioPorPersona = flight.getAmount();
                break;
            }
        }
        if (precioPorPersona == 0)
            throw new Exception("The flight number '" + payloadDTO.getFlightReservation().getFlightNumber() + "' doesn't exists.");

        validateOrigin(payloadDTO.getFlightReservation().getOrigin());
        validateDestination(payloadDTO.getFlightReservation().getDestination());

        amount = cantidadPersonas * precioPorPersona;
        if (payloadDTO.getFlightReservation().getPaymentMethod().getType().toLowerCase().equals("credit")){
            int cuotas = payloadDTO.getFlightReservation().getPaymentMethod().getDues();
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
        }else if(payloadDTO.getFlightReservation().getPaymentMethod().getType().toLowerCase().equals("debit")){
            if (payloadDTO.getFlightReservation().getPaymentMethod().getDues() != 1)
                throw new Exception("Dues must be 1 when payment method is debit card.");
            total = amount;
        }else
            throw new Exception("Payment method must be 'credit' or 'debit'.");

        FlightPayloadResponseDTO flightPayloadResponseDTO = new FlightPayloadResponseDTO();
        flightPayloadResponseDTO.setUserName(payloadDTO.getUserName());
        flightPayloadResponseDTO.setAmount(amount);
        flightPayloadResponseDTO.setInterest(interest);
        flightPayloadResponseDTO.setTotal(total);
        flightPayloadResponseDTO.setTotal(total);
        flightPayloadResponseDTO.setFlightReservationDTO(payloadDTO.getFlightReservation());
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO();
        statusCodeDTO.setCode(200);
        statusCodeDTO.setMessage("The process ended succesfully!");
        flightPayloadResponseDTO.setStatusCodeDTO(statusCodeDTO);
        return flightPayloadResponseDTO;
    }

    public List<FlightDTO> applyFilters(List<FlightDTO> flights) throws ParseException, InvalidDateException {
        if(flightsFilter.containsKey("dateFrom")) {
            LocalDate dateFrom = dateValidator.validateDate(flightsFilter.get("dateFrom")).plusDays(-1);
            flights = flights.stream().filter(a -> a.getDateFrom().isAfter((dateFrom))).collect(Collectors.toList());
        }
        if(flightsFilter.containsKey("dateTo")) {
            LocalDate dateTo = dateValidator.validateDate(flightsFilter.get("dateTo")).plusDays(1);
            flights = flights.stream().filter(a -> a.getDateTo().isBefore((dateTo))).collect(Collectors.toList());
        }
        if(flightsFilter.containsKey("origin")) {
            flights = flights.stream().filter(a -> a.getOrigin().toLowerCase().equals(flightsFilter.get("origin").toLowerCase())).collect(Collectors.toList());
        }
        if(flightsFilter.containsKey("destination")) {
            flights = flights.stream().filter(a -> a.getDestination().toLowerCase().equals(flightsFilter.get("destination").toLowerCase())).collect(Collectors.toList());
        }
        return flights;
    }

    public void validateDestination(String destination) throws IncorrectPlaceException {
        List<FlightDTO> fligths = flightRepository.getAllFlights();
        Boolean destinationExists = false;
        for (FlightDTO fligth : fligths) {
            if (fligth.getDestination().equals(destination)){
                destinationExists = true;
                break;
            }
        }
        if (!destinationExists)
            throw new IncorrectPlaceException("Destination '"+ destination +"' doesn't exists. Please try again.");
    }

    public void validateOrigin(String origin) throws IncorrectPlaceException {
        List<FlightDTO> fligths = flightRepository.getAllFlights();
        Boolean originExists = false;
        for (FlightDTO fligth : fligths) {
            if (fligth.getOrigin().equals(origin)){
                originExists = true;
                break;
            }
        }
        if (!originExists)
            throw new IncorrectPlaceException("Origin '"+ origin +"' doesn't exists. Please try again.");
    }
}
