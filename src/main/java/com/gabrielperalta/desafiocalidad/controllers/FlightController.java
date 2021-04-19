package com.gabrielperalta.desafiocalidad.controllers;

import com.gabrielperalta.desafiocalidad.dto.FlightPayLoadDTO;
import com.gabrielperalta.desafiocalidad.dto.StatusCodeDTO;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidMailException;
import com.gabrielperalta.desafiocalidad.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("api/v2/")
public class FlightController {

    FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/flights")
    public ResponseEntity getFlights(@RequestParam Map<String, String> requestParams) throws IncorrectPlaceException, InvalidDateException, ParseException, EmptySearchException {
        return new ResponseEntity(flightService.getFlights(requestParams), HttpStatus.OK);
    }

    @PostMapping("/flight-reservation")
    public ResponseEntity createPayLoad(@RequestBody FlightPayLoadDTO flightPayLoadDTO) throws Exception {
        return new ResponseEntity(flightService.createPayload(flightPayLoadDTO), HttpStatus.CREATED);
    }

    @ExceptionHandler(EmptySearchException.class)
    public ResponseEntity handlerSearch(Exception exception){
        StatusCodeDTO error = new StatusCodeDTO();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity handlerInvalidDate(Exception exception){
        StatusCodeDTO error = new StatusCodeDTO();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMailException.class)
    public ResponseEntity handlerInvalidMail(Exception exception){
        StatusCodeDTO error = new StatusCodeDTO();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handlerException(Exception exception){
        StatusCodeDTO error = new StatusCodeDTO();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
