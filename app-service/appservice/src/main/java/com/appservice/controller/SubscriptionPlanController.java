package com.appservice.controller;

import com.appservice.dto.EmployeeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionPlanDTO;
import com.appservice.service.SubscriptionPlanService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription-plan")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(final SubscriptionPlanService subscriptionPlanService){
        this.subscriptionPlanService=subscriptionPlanService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SubscriptionPlanDTO subscriptionPlanDTO) {
        return this.subscriptionPlanService.create(subscriptionPlanDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody SubscriptionPlanDTO subscriptionPlanDTO) {
        return this.subscriptionPlanService.update(id, subscriptionPlanDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.subscriptionPlanService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.subscriptionPlanService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.subscriptionPlanService.remove(id);
    }
}
