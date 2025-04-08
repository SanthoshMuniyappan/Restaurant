package com.appservice.exception;

public class BadServiceAlertException extends RuntimeException {
    public BadServiceAlertException(String message) {
        super(message);
    }
}
