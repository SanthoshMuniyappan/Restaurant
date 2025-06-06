package com.appservice.exception;

public class EmployeeNotFoundException extends RuntimeException {

    private String message;
    private String endpoint;
    private String createdBy;

    public EmployeeNotFoundException(String message,String endpoint,String createdBy) {
        super(message);
        this.message = message;
        this.endpoint = endpoint;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
