package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionDTO;
import com.appservice.service.SubscriptionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant-table")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(final SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/generate/{tableName}")
    public ResponseDTO generateQRCode(@PathVariable final String tableName, @RequestBody final SubscriptionDTO subscriptionDTO) {
        return subscriptionService.generateQRCode(tableName, subscriptionDTO);
    }

    @GetMapping("/retrieve-all/{restaurantId}")
    public ResponseDTO retrieveAllById(@PathVariable final String restaurantId) {
        return this.subscriptionService.retrieveAllById(restaurantId);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.subscriptionService.remove(id);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable("id") final String id) {
        return this.subscriptionService.retrieveById(id);
    }
}
