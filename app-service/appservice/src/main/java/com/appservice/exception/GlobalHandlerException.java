package com.appservice.exception;

import com.appservice.dto.ResponseDTO;
import com.appservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleException(Exception exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(BadServiceAlertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleBadRequestAlertException(BadServiceAlertException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleRestaurantNotFoundException(RestaurantNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleCustomerNotFoundException(CustomerNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleProductsNotFoundException(ProductsNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleOrderNotFoundException(OrderNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(OrderItemsNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleOrderItemsNotFoundException(OrderItemsNotFoundException exception) {
        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }
}