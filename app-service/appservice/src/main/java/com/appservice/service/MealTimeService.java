package com.appservice.service;

import com.appservice.dto.MealTimeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.MealTimeRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.MealTime;

import java.util.List;

@Service
public class MealTimeService {

    private final MealTimeRepository mealTimeRepository;

    public MealTimeService(final MealTimeRepository mealTimeRepository) {
        this.mealTimeRepository = mealTimeRepository;
    }

    @Transactional
    public ResponseDTO create(final MealTimeDTO mealTimeDTO) {
        final MealTime mealTime = new MealTime();
        mealTime.setName(mealTime.getName());
        mealTime.setCreatedBy(mealTimeDTO.getCreatedBy());
        mealTime.setUpdatedBy(mealTime.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.mealTimeRepository.save(mealTime), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final MealTimeDTO mealTimeDTO) {
        final MealTime mealTime = this.mealTimeRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND));

        if (mealTime.getName() != null) {
            mealTime.setName(mealTimeDTO.getName());
        }
        return new ResponseDTO(Constants.UPDATED, this.mealTimeRepository.save(mealTime), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final MealTime mealTime = this.mealTimeRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, mealTime, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        List<MealTime> mealTimes = this.mealTimeRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED, mealTimes, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO remove(final String id) {
        if (this.mealTimeRepository.existsById(id)) {
            this.mealTimeRepository.deleteById(id);
        } else {
            throw new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
