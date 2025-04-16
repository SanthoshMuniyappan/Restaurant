package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionPlanDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.SubscriptionPlanRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.SubscriptionPlan;

import java.util.List;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final AuthenticationService authenticationService;

    public SubscriptionPlanService(final SubscriptionPlanRepository subscriptionPlanRepository, final AuthenticationService authenticationService) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final SubscriptionPlanDTO subscriptionPlanDTO) {
        final SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setName(subscriptionPlanDTO.getName());
        subscriptionPlan.setPrice(subscriptionPlanDTO.getPrice());
        subscriptionPlan.setSubscriptionType(subscriptionPlanDTO.getSubscriptionType());
        subscriptionPlan.setDurationDays(subscriptionPlanDTO.getDurationDays());
        subscriptionPlan.setCreatedBy(authenticationService.getUserId());
        subscriptionPlan.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.subscriptionPlanRepository.save(subscriptionPlan), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SubscriptionPlanDTO subscriptionPlanDTO) {
        final SubscriptionPlan subscriptionPlan = this.subscriptionPlanRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND, "api/v1/subscription-plan/update/{id}", authenticationService.getUserId()));

        if (subscriptionPlanDTO.getPrice() != null) {
            subscriptionPlan.setPrice(subscriptionPlanDTO.getPrice());
        }
        subscriptionPlan.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.subscriptionPlanRepository.save(subscriptionPlan), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final SubscriptionPlan subscriptionPlan = this.subscriptionPlanRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND, "api/v1/subscription-plan/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, subscriptionPlan, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.subscriptionPlanRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subscriptionPlanRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND, "api/v1/subscription-plan/remove/{id}", authenticationService.getUserId());
        }
        this.subscriptionPlanRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
