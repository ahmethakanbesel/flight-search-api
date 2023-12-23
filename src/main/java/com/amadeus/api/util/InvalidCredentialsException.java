package com.amadeus.api.util;


public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super();
    }

    public InvalidCredentialsException(final String message) {
        super(message);
    }

}
