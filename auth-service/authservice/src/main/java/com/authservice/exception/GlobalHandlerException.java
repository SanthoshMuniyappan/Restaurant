package com.authservice.exception;

import com.authservice.dto.ResponseDTO;
import com.authservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BadRequestServiceAlertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestServiceAlertException(BadRequestServiceAlertException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }
}
