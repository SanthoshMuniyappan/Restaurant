package com.appservice.controller;

import com.appservice.dto.ProductsDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.ProductsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(final ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final ProductsDTO productsDTO) {
        return this.productsService.create(productsDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody ProductsDTO productsDTO) {
        return this.productsService.update(id, productsDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.productsService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.productsService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.productsService.remove(id);
    }

    @GetMapping("/retrieve-rest-products/{id}")
    public ResponseDTO retrieveAllByRestaurantId(@PathVariable("id") final String id) {
        return this.productsService.retrieveAllByRestaurantId(id);
    }
}
