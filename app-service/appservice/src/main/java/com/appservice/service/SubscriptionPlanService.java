package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionPlanDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.SubscriptionPlanRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.SubscriptionPlan;

import java.util.List;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(final SubscriptionPlanRepository subscriptionPlanRepository){
        this.subscriptionPlanRepository=subscriptionPlanRepository;
    }

    @Transactional
    public ResponseDTO create(final SubscriptionPlanDTO subscriptionPlanDTO){
        final SubscriptionPlan subscriptionPlan=new SubscriptionPlan();
        subscriptionPlan.setName(subscriptionPlanDTO.getName());
        subscriptionPlan.setPrice(subscriptionPlanDTO.getPrice());
        subscriptionPlan.setSubscriptionType(subscriptionPlanDTO.getSubscriptionType());
        subscriptionPlan.setDurationDays(subscriptionPlanDTO.getDurationDays());
        subscriptionPlan.setCreatedBy(subscriptionPlanDTO.getCreatedBy());
        subscriptionPlan.setUpdatedBy(subscriptionPlanDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED,this.subscriptionPlanRepository.save(subscriptionPlan), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SubscriptionPlanDTO subscriptionPlanDTO){
        final SubscriptionPlan subscriptionPlan=this.subscriptionPlanRepository.findById(id).orElseThrow(()->new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND));

        if (subscriptionPlanDTO.getPrice()!=null){
            subscriptionPlan.setPrice(subscriptionPlanDTO.getPrice());
        }

        return new ResponseDTO(Constants.UPDATED,this.subscriptionPlanRepository.save(subscriptionPlan),HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id){
        final SubscriptionPlan subscriptionPlan=this.subscriptionPlanRepository.findById(id).orElseThrow(()->new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED,subscriptionPlan,HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll(){
        List<SubscriptionPlan> subscriptionPlanList=this.subscriptionPlanRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED,subscriptionPlanList,HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id){
        if (this.subscriptionPlanRepository.existsById(id)){
            this.subscriptionPlanRepository.deleteById(id);
        }else {
            throw new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED,id,HttpStatus.OK.getReasonPhrase());
    }
}
