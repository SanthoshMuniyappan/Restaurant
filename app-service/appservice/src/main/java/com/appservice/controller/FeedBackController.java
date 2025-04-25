package com.appservice.controller;

import com.appservice.dto.FeedBackDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.FeedBackService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    private final FeedBackService feedBackService;

    public FeedBackController(final FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody FeedBackDTO feedBackDTO) {
        return this.feedBackService.create(feedBackDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.feedBackService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.feedBackService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.feedBackService.remove(id);
    }
}
