package com.appservice.controller;

import com.appservice.dto.MealTimeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.MealTimeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meal-time")
public class MealTimeController {

    private final MealTimeService mealTimeService;

    public MealTimeController(final MealTimeService mealTimeService) {
        this.mealTimeService = mealTimeService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final MealTimeDTO mealTimeDTO) {
        return this.mealTimeService.create(mealTimeDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final MealTimeDTO mealTimeDTO) {
        return this.mealTimeService.update(id, mealTimeDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.mealTimeService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.mealTimeService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.mealTimeService.remove(id);
    }
}
