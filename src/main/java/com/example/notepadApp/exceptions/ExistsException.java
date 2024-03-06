package com.example.notepadApp.exceptions;

public class ExistsException extends RuntimeException {

    public ExistsException(String message) {
        super(message);
    }

}