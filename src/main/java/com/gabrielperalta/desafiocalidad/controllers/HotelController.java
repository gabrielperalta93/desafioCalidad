package com.gabrielperalta.desafiocalidad.controllers;

import com.gabrielperalta.desafiocalidad.dto.HotelPayloadDTO;
import com.gabrielperalta.desafiocalidad.dto.StatusCodeDTO;
import com.gabrielperalta.desafiocalidad.exceptions.EmptySearchException;
import com.gabrielperalta.desafiocalidad.exceptions.IncorrectPlaceException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidDateException;
import com.gabrielperalta.desafiocalidad.exceptions.InvalidMailException;
import com.gabrielperalta.desafiocalidad.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class HotelController {

    private HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(value = "/hotels")
    public ResponseEntity getHotels(@RequestParam Map<String, String> requestParams) throws ParseException, InvalidDateException, EmptySearchException, IncorrectPlaceException {
        return new ResponseEntity(hotelService.getHotels(requestParams), HttpStatus.OK);
    }

    @PostMapping("/booking")
    public ResponseEntity createPayLoad(@RequestBody HotelPayloadDTO payloadDTO) throws Exception {
        return new ResponseEntity(hotelService.createPayload(payloadDTO), HttpStatus.CREATED);
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
