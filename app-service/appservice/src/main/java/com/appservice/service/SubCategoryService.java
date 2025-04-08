package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubCategoryDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.SubCategoryRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.SubCategory;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(final SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Transactional
    public ResponseDTO create(final SubCategoryDTO subCategoryDTO) {
        final SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDTO.getName());
        subCategory.setCreatedBy(subCategoryDTO.getCreatedBy());
        subCategory.setUpdatedBy(subCategoryDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.subCategoryRepository.save(subCategory), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final SubCategoryDTO subCategoryDTO) {
        final SubCategory subCategory = this.subCategoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND));

        if (subCategoryDTO.getName() != null) {
            subCategory.setName(subCategoryDTO.getName());
        }
        return new ResponseDTO(Constants.UPDATED, this.subCategoryRepository.save(subCategory), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final SubCategory subCategory = this.subCategoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, subCategory, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        List<SubCategory> subCategories = this.subCategoryRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED, subCategories, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.subCategoryRepository.existsById(id)) {
            this.subCategoryRepository.deleteById(id);
        } else {
            throw new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
