package com.gabrielperalta.desafiocalidad.exceptions;

public class InvalidMailException extends Exception{
    public InvalidMailException(String error){
        super(error);
    }
}
