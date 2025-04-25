package com.appservice.exception;

import com.appservice.dto.ResponseDTO;
import com.appservice.repository.ErrorRepository;
import com.appservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ors.common.model.Error;

@RestControllerAdvice
public class GlobalHandlerException {

    @Autowired
    private ErrorRepository errorRepository;

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

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

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ImageRequestServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleImageRequestServiceException(ImageRequestServiceException exception) {

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlePaymentNotFoundException(PaymentNotFoundException exception) {

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(FeedBackNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleFeedBackNotFoundException(FeedBackNotFoundException exception) {

        final Error error=new Error();
        error.setErrorMessage(exception.getMessage());
        error.setEndPoint(exception.getEndpoint());
        error.setCreatedBy(exception.getCreatedBy());
        this.errorRepository.save(error);

        ResponseDTO responseDTO = new ResponseDTO();
        exception.printStackTrace();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDTO.setMessage(Constants.NOT_FOUND);
        responseDTO.setData(exception.getMessage());
        return responseDTO;
    }
}