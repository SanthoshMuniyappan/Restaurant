package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.VegOrNonVegDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.VegOrNonVegRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.VegOrNonVeg;

import java.util.List;

@Service
public class VegOrNonVegService {

    private final VegOrNonVegRepository vegOrNonVegRepository;

    private final AuthenticationService authenticationService;

    public VegOrNonVegService(final VegOrNonVegRepository vegOrNonVegRepository, final AuthenticationService authenticationService) {
        this.vegOrNonVegRepository = vegOrNonVegRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final VegOrNonVegDTO vegOrNonVegDTO) {
        final VegOrNonVeg vegOrNonVeg = new VegOrNonVeg();
        vegOrNonVeg.setName(vegOrNonVegDTO.getName());
        vegOrNonVeg.setCreatedBy(authenticationService.getUserId());
        vegOrNonVeg.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.vegOrNonVegRepository.save(vegOrNonVeg), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final VegOrNonVegDTO vegOrNonVegDTO) {
        final VegOrNonVeg vegOrNonVeg = this.vegOrNonVegRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND, "api/v1/veg-or-non-veg/update/{id}", authenticationService.getUserId()));

        if (vegOrNonVegDTO.getName() != null) {
            vegOrNonVeg.setName(vegOrNonVegDTO.getName());
        }
        vegOrNonVeg.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.vegOrNonVegRepository.save(vegOrNonVeg), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final VegOrNonVeg vegOrNonVeg = this.vegOrNonVegRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND, "api/v1/veg-or-non-veg/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, vegOrNonVeg, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED,  this.vegOrNonVegRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (!this.vegOrNonVegRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND, "api/v1/veg-or-non-veg/remove/{id}", authenticationService.getUserId());
        }
        this.vegOrNonVegRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
