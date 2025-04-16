package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.ImageRequestServiceException;
import com.appservice.repository.VideoRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ors.common.model.Video;

import java.io.IOException;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    private final AuthenticationService authenticationService;

    public VideoService(final VideoRepository videoRepository, final AuthenticationService authenticationService) {
        this.videoRepository = videoRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final MultipartFile file, final String foodName) throws IOException {
        final Video video = new Video();
        video.setVideo(file.getBytes());
        video.setFoodName(foodName);
        video.setCreatedBy(authenticationService.getUserId());
        video.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.videoRepository.save(video), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.videoRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveId(final String id) {
        final Video video = this.videoRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.NOT_FOUND, "api/v1/video/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, video, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateImage(final String id, final MultipartFile file, final String foodName) throws IOException {
        final Video video = this.videoRepository.findById(id).orElseThrow(() -> new ImageRequestServiceException(Constants.NOT_FOUND, "api/v1/video/update/{id}", authenticationService.getUserId()));
        if (file != null) {
            video.setVideo(file.getBytes());
        }
        if (foodName != null) {
            video.setFoodName(foodName);
        }
        video.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.videoRepository.save(video), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeImage(final String id) {
        if (!this.videoRepository.existsById(id)) {
            throw new ImageRequestServiceException(Constants.NOT_FOUND, "api/v1/video/remove/{id}", authenticationService.getUserId());
        }
        this.videoRepository.deleteById(id)
        ;
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());

    }
}
