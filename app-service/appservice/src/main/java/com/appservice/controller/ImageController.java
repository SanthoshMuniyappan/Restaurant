package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.service.ImageService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/create")
    public ResponseDTO createImage(@RequestParam("file") final MultipartFile file, @RequestParam("foodName") final String foodName) throws IOException {
        return this.imageService.createImage(file, foodName);
    }

    @GetMapping("/retrieveAll")
    public ResponseDTO retrieveAll() {
        return this.imageService.retrieveAll();
    }

    @GetMapping("/retrieveId/{id}")
    public ResponseDTO retrieveId(@PathVariable final String id) {
        return this.imageService.retrieveId(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateImage(@PathVariable final String id, @RequestParam("file") final MultipartFile file, @RequestParam("foodName") final String foodName) throws IOException {
        return this.imageService.updateImage(id, file, foodName);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO removeImage(@PathVariable final String id) {
        return this.imageService.removeImage(id);
    }
}
