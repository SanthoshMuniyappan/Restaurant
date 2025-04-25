package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.RestaurantDTO;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.RestaurantRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Restaurant;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final AuthenticationService authenticationService;

    public RestaurantService(final RestaurantRepository restaurantRepository, final AuthenticationService authenticationService) {
        this.restaurantRepository = restaurantRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final RestaurantDTO restaurantDTO) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setRatings(restaurantDTO.getRatings());
        restaurant.setAppRating(restaurantDTO.getAppRating());
        restaurant.setLocation(restaurantDTO.getLocation());
        restaurant.setStartRate(restaurantDTO.getStartRate());
        restaurant.setCreatedBy(authenticationService.getUserId());
        restaurant.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.restaurantRepository.save(restaurant), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final RestaurantDTO restaurantDTO) {
        final Restaurant restaurant = this.restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/restaurant/update/{id}", authenticationService.getUserId()));

        if (restaurantDTO.getName() != null) {
            restaurant.setName(restaurantDTO.getName());
        }
        if (restaurantDTO.getLocation() != null) {
            restaurant.setLocation(restaurantDTO.getLocation());
        }
        if (restaurantDTO.getStartRate() != null) {
            restaurant.setStartRate(restaurantDTO.getStartRate());
        }
        restaurant.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.restaurantRepository.save(restaurant), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Restaurant restaurant = this.restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/restaurant/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, restaurant, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.restaurantRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }


    public ResponseDTO remove(final String id) {
        if (!this.restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/restaurant/remove/{id}", authenticationService.getUserId());
        }
        this.restaurantRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
