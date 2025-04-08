package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.VegOrNonVegDTO;
import com.appservice.service.VegOrNonVegService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veg-or-non-veg")
public class VegOrNonVegController {

    private final VegOrNonVegService vegOrNonVegService;

    public VegOrNonVegController(final VegOrNonVegService vegOrNonVegService) {
        this.vegOrNonVegService = vegOrNonVegService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final VegOrNonVegDTO vegOrNonVegDTO) {
        return this.vegOrNonVegService.create(vegOrNonVegDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final VegOrNonVegDTO vegOrNonVegDTO) {
        return this.vegOrNonVegService.update(id, vegOrNonVegDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.vegOrNonVegService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.vegOrNonVegService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.vegOrNonVegService.remove(id);
    }
}
