package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.VegOrNonVegDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.VegOrNonVegRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.VegOrNonVeg;

import java.util.List;

@Service
public class VegOrNonVegService {

    private final VegOrNonVegRepository vegOrNonVegRepository;

    public VegOrNonVegService(final VegOrNonVegRepository vegOrNonVegRepository) {
        this.vegOrNonVegRepository = vegOrNonVegRepository;
    }

    @Transactional
    public ResponseDTO create(final VegOrNonVegDTO vegOrNonVegDTO) {
        final VegOrNonVeg vegOrNonVeg = new VegOrNonVeg();
        vegOrNonVeg.setName(vegOrNonVegDTO.getName());
        vegOrNonVeg.setCreatedBy(vegOrNonVegDTO.getCreatedBy());
        vegOrNonVeg.setUpdatedBy(vegOrNonVegDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.vegOrNonVegRepository.save(vegOrNonVeg), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final VegOrNonVegDTO vegOrNonVegDTO) {
        final VegOrNonVeg vegOrNonVeg = this.vegOrNonVegRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND));

        if (vegOrNonVegDTO.getName() != null) {
            vegOrNonVeg.setName(vegOrNonVegDTO.getName());
        }
        return new ResponseDTO(Constants.UPDATED, this.vegOrNonVegRepository.save(vegOrNonVeg), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final VegOrNonVeg vegOrNonVeg = this.vegOrNonVegRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, vegOrNonVeg, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        List<VegOrNonVeg> vegOrNonVegs = this.vegOrNonVegRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED, vegOrNonVegs, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (this.vegOrNonVegRepository.existsById(id)) {
            this.vegOrNonVegRepository.deleteById(id);
        } else {
            throw new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
