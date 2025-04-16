package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.exception.ImageRequestServiceException;
import com.appservice.repository.ImageRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ors.common.model.Image;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final AuthenticationService authenticationService;

    public ImageService(final ImageRepository imageRepository, final AuthenticationService authenticationService) {
        this.imageRepository = imageRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createImage(final MultipartFile file, final String foodName) throws IOException {
        final Image image = new Image();
        image.setImage(file.getBytes());
        image.setFoodName(foodName);
        image.setCreatedBy(authenticationService.getUserId());
        image.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.imageRepository.save(image), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.imageRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveId(final String id) {
        final Image image = this.imageRepository.findById(id).orElseThrow(() -> new ImageRequestServiceException(Constants.IMAGE_ID_NOT_FOUND, "api/v1/image/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, image, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateImage(final String id, final MultipartFile file, final String foodName) throws IOException {
        final Image image = this.imageRepository.findById(id).orElseThrow(() -> new ImageRequestServiceException(Constants.NOT_FOUND, "api/v1/image/update/{id}", authenticationService.getUserId()));
        if (file != null) {
            image.setImage(file.getBytes());
        }
        if (foodName != null) {
            image.setFoodName(foodName);
        }
        image.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.imageRepository.save(image), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeImage(final String id) {
        if (!this.imageRepository.existsById(id)) {
            throw new ImageRequestServiceException(Constants.NOT_FOUND, "api/v1/image/remove/{id}", authenticationService.getUserId());
        }
        this.imageRepository.deleteById(id)
        ;
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());

    }
}
