package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubCategoryDTO;
import com.appservice.service.SubCategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub-category")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(final SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final SubCategoryDTO subCategoryDTO) {
        return this.subCategoryService.create(subCategoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final SubCategoryDTO subCategoryDTO) {
        return this.subCategoryService.update(id, subCategoryDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.subCategoryService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.subCategoryService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.subCategoryService.remove(id);
    }
}
