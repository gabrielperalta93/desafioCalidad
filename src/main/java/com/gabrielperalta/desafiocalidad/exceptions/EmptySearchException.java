package com.gabrielperalta.desafiocalidad.exceptions;

public class EmptySearchException extends Exception{
    public EmptySearchException(){
        super("Your search didn't find any result.");
    }
}
