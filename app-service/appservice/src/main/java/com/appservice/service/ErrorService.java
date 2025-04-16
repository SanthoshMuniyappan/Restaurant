package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.ErrorRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Error;

import java.util.List;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    private final AuthenticationService authenticationService;

    public ErrorService(final ErrorRepository errorRepository, final AuthenticationService authenticationService) {
        this.errorRepository = errorRepository;
        this.authenticationService = authenticationService;
    }

    public ResponseDTO retrieve(final String id) {
        final Error error = this.errorRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.ERROR_ID_NOT_FOUND, "api/v1/error/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, error, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.errorRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (!this.errorRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.EMPLOYEE_ID_NOT_FOUND, "api/v1/error/remove/{id}", authenticationService.getUserId());
        }
        this.errorRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
