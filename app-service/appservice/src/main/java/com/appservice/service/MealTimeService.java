package com.appservice.service;

import com.appservice.dto.MealTimeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.MealTimeRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.MealTime;

import java.util.List;

@Service
public class MealTimeService {

    private final MealTimeRepository mealTimeRepository;

    private final AuthenticationService authenticationService;

    public MealTimeService(final MealTimeRepository mealTimeRepository, final AuthenticationService authenticationService) {
        this.mealTimeRepository = mealTimeRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final MealTimeDTO mealTimeDTO) {
        final MealTime mealTime = new MealTime();
        mealTime.setName(mealTimeDTO.getName());
        mealTime.setCreatedBy(authenticationService.getUserId());
        mealTime.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.mealTimeRepository.save(mealTime), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final MealTimeDTO mealTimeDTO) {
        final MealTime mealTime = this.mealTimeRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND, "api/v1/meal-time/update/{id}", authenticationService.getUserId()));

        if (mealTime.getName() != null) {
            mealTime.setName(mealTimeDTO.getName());
        }
        mealTime.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.mealTimeRepository.save(mealTime), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final MealTime mealTime = this.mealTimeRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND, "api/v1/meal-time/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, mealTime, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.mealTimeRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (!this.mealTimeRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND, "api/v1/meal-time/remove/{id}", authenticationService.getUserId());
        }
        this.mealTimeRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
