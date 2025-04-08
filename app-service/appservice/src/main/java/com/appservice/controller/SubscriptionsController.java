package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionsDTO;
import com.appservice.service.SubscriptionsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant-table")
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;

    public SubscriptionsController(final SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @PostMapping("/generate/{tableName}")
    public ResponseDTO generateQRCode(@PathVariable final String tableName, @RequestBody final SubscriptionsDTO subscriptionsDTO) {
        return subscriptionsService.generateQRCode(tableName, subscriptionsDTO);
    }

    @GetMapping("/retrieve-all/{restaurantId}")
    public ResponseDTO retrieveAllById(@PathVariable final String restaurantId) {
        return this.subscriptionsService.retrieveAllById(restaurantId);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.subscriptionsService.remove(id);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable("id") final String id) {
        return this.subscriptionsService.retrieveById(id);
    }
}
