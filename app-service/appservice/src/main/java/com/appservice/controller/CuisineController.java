package com.appservice.controller;

import com.appservice.dto.CuisineDto;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.CuisineService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuisine")
public class CuisineController {

    private final CuisineService cuisineService;

    public CuisineController(final CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final CuisineDto cuisineDto) {
        return this.cuisineService.create(cuisineDto);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final CuisineDto cuisineDto) {
        return this.cuisineService.update(id, cuisineDto);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.cuisineService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.cuisineService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.cuisineService.remove(id);
    }
}
