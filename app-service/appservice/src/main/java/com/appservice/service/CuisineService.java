package com.appservice.service;

import com.appservice.dto.CuisineDto;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.CuisineRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Cuisine;

import java.util.List;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;
    private final AuthenticationService authenticationService;

    public CuisineService(final CuisineRepository cuisineRepository, final AuthenticationService authenticationService) {
        this.cuisineRepository = cuisineRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final CuisineDto cuisineDto) {
        final Cuisine cuisine = new Cuisine();
        cuisine.setName(cuisineDto.getName());
        cuisine.setCreatedBy(authenticationService.getUserId());
        cuisine.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.cuisineRepository.save(cuisine), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final CuisineDto cuisineDto) {
        final Cuisine cuisine = this.cuisineRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND, "api/v1/cuisine/update/{id}", authenticationService.getUserId()));

        if (cuisineDto.getName() != null) {
            cuisine.setName(cuisineDto.getName());
        }
        cuisine.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.cuisineRepository.save(cuisine), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Cuisine cuisine = this.cuisineRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND, "api/v1/cuisine/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, cuisine, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.cuisineRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (!this.cuisineRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND, "api/v1/cuisine/remove/{id}", authenticationService.getUserId());
        }
        this.cuisineRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
