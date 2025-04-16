package com.appservice.controller;

import com.appservice.dto.ResponseDTO;
import com.appservice.service.ImageService;
import com.appservice.service.VideoService;
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
@RequestMapping("/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController( final VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/create")
    public ResponseDTO createImage(@RequestParam("file") final MultipartFile file, @RequestParam("foodName") final String foodName) throws IOException {
        return this.videoService.create(file, foodName);
    }

    @GetMapping("/retrieveAll")
    public ResponseDTO retrieveAll() {
        return this.videoService.retrieveAll();
    }

    @GetMapping("/retrieveId/{id}")
    public ResponseDTO retrieveId(@PathVariable final String id) {
        return this.videoService.retrieveId(id);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateImage(@PathVariable final String id, @RequestParam("file") final MultipartFile file, @RequestParam("foodName") final String foodName) throws IOException {
        return this.videoService.updateImage(id, file, foodName);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO removeImage(@PathVariable final String id) {
        return this.videoService.removeImage(id);
    }
}
