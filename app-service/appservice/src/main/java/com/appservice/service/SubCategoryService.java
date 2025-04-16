package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubCategoryDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.SubCategoryRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.SubCategory;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final AuthenticationService authenticationService;

    public SubCategoryService(final SubCategoryRepository subCategoryRepository, final AuthenticationService authenticationService) {
        this.subCategoryRepository = subCategoryRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final SubCategoryDTO subCategoryDTO) {
        final SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDTO.getName());
        subCategory.setCreatedBy(authenticationService.getUserId());
        subCategory.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.subCategoryRepository.save(subCategory), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SubCategoryDTO subCategoryDTO) {
        final SubCategory subCategory = this.subCategoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND, "api/v1/sub-category/update/{id}", authenticationService.getUserId()));

        if (subCategoryDTO.getName() != null) {
            subCategory.setName(subCategoryDTO.getName());
        }
        subCategory.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.subCategoryRepository.save(subCategory), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final SubCategory subCategory = this.subCategoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND, "api/v1/sub-category/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, subCategory, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.subCategoryRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subCategoryRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND, "api/v1/sub-category/remove/{id}", authenticationService.getUserId());
        }
        this.subCategoryRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
