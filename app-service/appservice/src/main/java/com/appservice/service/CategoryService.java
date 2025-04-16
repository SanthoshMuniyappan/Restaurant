package com.appservice.service;

import com.appservice.dto.CategoryDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.CategoryRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Category;

import java.util.List;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;

    public CategoryService(final CategoryRepository categoryRepository, final AuthenticationService authenticationService) {
        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setCreatedBy(authenticationService.getUserId());
        category.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.categoryRepository.save(category), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final CategoryDTO categoryDTO) {
        final Category category = this.categoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CATEGORY_ID_NOT_FOUND, "api/v1/category/update/{id}", authenticationService.getUserId()));

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        category.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.categoryRepository.save(category), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Category category = this.categoryRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.CATEGORY_ID_NOT_FOUND, "api/v1/category/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, category, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.categoryRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.categoryRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.CATEGORY_ID_NOT_FOUND, "api/v1/category/remove/{id}", authenticationService.getUserId());
        }
        this.categoryRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
