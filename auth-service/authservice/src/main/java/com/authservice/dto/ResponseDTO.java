package com.authservice.dto;

public class ResponseDTO {

    private String message;
    private Object data;
    private String status;

    public ResponseDTO() {

    }

    public ResponseDTO(String message, Object data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
