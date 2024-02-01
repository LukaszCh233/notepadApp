package com.example.notepadApp.exceptions;

public class ExistsException extends RuntimeException {

    public ExistsException() {
        super();
    }

    public ExistsException(String message) {
        super(message);
    }

    public ExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}