package com.authservice.exception;

public class BadRequestServiceAlertException extends RuntimeException {
    public BadRequestServiceAlertException(String message) {
        super(message);
    }
}
