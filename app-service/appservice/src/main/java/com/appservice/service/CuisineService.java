package com.appservice.service;

import com.appservice.dto.CuisineDto;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.CuisineRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Cuisine;

import java.util.List;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(final CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Transactional
    public ResponseDTO create(final CuisineDto cuisineDto) {
        final Cuisine cuisine = new Cuisine();
        cuisine.setName(cuisine.getName());
        cuisine.setCreatedBy(cuisineDto.getCreatedBy());
        cuisine.setUpdatedBy(cuisineDto.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.cuisineRepository.save(cuisine), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final CuisineDto cuisineDto) {
        final Cuisine cuisine = this.cuisineRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND));

        if (cuisineDto.getName() != null) {
            cuisine.setName(cuisineDto.getName());
        }
        return new ResponseDTO(Constants.UPDATED, this.cuisineRepository.save(cuisine), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Cuisine cuisine = this.cuisineRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, cuisine, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        List<Cuisine> cuisineList = this.cuisineRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED, cuisineList, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (this.cuisineRepository.existsById(id)) {
            this.cuisineRepository.deleteById(id);
        } else {
            throw new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
