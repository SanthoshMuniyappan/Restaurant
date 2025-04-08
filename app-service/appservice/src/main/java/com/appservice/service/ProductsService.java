package com.appservice.service;

import com.appservice.dto.ProductsDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.ProductsNotFoundException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.CategoryRepository;
import com.appservice.repository.CuisineRepository;
import com.appservice.repository.MealTimeRepository;
import com.appservice.repository.ProductsRepository;
import com.appservice.repository.RestaurantRepository;
import com.appservice.repository.SubCategoryRepository;
import com.appservice.repository.VegOrNonVegRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Category;
import ors.common.model.Cuisine;
import ors.common.model.MealTime;
import ors.common.model.Products;
import ors.common.model.Restaurant;
import ors.common.model.SubCategory;
import ors.common.model.VegOrNonVeg;

import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    private final RestaurantRepository restaurantRepository;

    private final CuisineRepository cuisineRepository;

    private final MealTimeRepository mealTimeRepository;

    private final CategoryRepository categoryRepository;

    private final VegOrNonVegRepository vegOrNonVegRepository;

    private final SubCategoryRepository subCategoryRepository;

    public ProductsService(final ProductsRepository productsRepository, final RestaurantRepository restaurantRepository, final CuisineRepository cuisineRepository, final MealTimeRepository mealTimeRepository, final CategoryRepository categoryRepository, final VegOrNonVegRepository vegOrNonVegRepository, final SubCategoryRepository subCategoryRepository) {
        this.productsRepository = productsRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.cuisineRepository = cuisineRepository;
        this.mealTimeRepository = mealTimeRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.vegOrNonVegRepository = vegOrNonVegRepository;
    }

    @Transactional
    public ResponseDTO create(final ProductsDTO productsDTO) {
        final Products products = new Products();
        final Restaurant restaurant = this.restaurantRepository.findById(productsDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND));
        final Cuisine cuisine = this.cuisineRepository.findById(productsDTO.getCuisineId()).orElseThrow(() -> new BadServiceAlertException(Constants.CUISINE_ID_NOT_FOUND));
        final MealTime mealTime = this.mealTimeRepository.findById(productsDTO.getMealTimeId()).orElseThrow(() -> new BadServiceAlertException(Constants.MEAL_TIME_ID_NOT_FOUND));
        final Category category = this.categoryRepository.findById(productsDTO.getCategoryId()).orElseThrow(() -> new BadServiceAlertException(Constants.CATEGORY_ID_NOT_FOUND));
        final VegOrNonVeg vegOrNonVeg = this.vegOrNonVegRepository.findById(productsDTO.getVegOrNonVegId()).orElseThrow(() -> new BadServiceAlertException(Constants.VEG_OR_NON_VEG_ID_NOT_FOUND));
        final SubCategory subCategory = this.subCategoryRepository.findById(productsDTO.getSubCategoryId()).orElseThrow(() -> new BadServiceAlertException(Constants.SUB_CATEGORY_ID_NOT_FOUND));
        products.setName(productsDTO.getName());
        products.setCuisine(cuisine);
        products.setMealTime(mealTime);
        products.setCategory(category);
        products.setVegOrNonVeg(vegOrNonVeg);
        products.setSubCategory(subCategory);
        products.setPrice(productsDTO.getPrice());
        products.setRestaurant(restaurant);
        products.setCreatedBy(productsDTO.getCreatedBy());
        products.setUpdatedBy(productsDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.productsRepository.save(products), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final ProductsDTO productsDTO) {
        final Products products = this.productsRepository.findById(id).orElseThrow(() -> new ProductsNotFoundException(Constants.PRODUCT_ID_NOT_FOUND));

        if (productsDTO.getName() != null) {
            products.setName(productsDTO.getName());
        }
        if (productsDTO.getPrice() != null) {
            products.setPrice(productsDTO.getPrice());
        }
        return new ResponseDTO(Constants.UPDATED, this.productsRepository.save(products), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Products products = this.productsRepository.findById(id).orElseThrow(() -> new ProductsNotFoundException(Constants.PRODUCT_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, products, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.productsRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.productsRepository.existsById(id)) {
            this.productsRepository.deleteById(id);
        } else {
            throw new ProductsNotFoundException(Constants.PRODUCT_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAllByRestaurantId(final String id) {
        final List<Products> products = this.productsRepository.findAllProductsByRestaurant(id);
        return new ResponseDTO(Constants.CREATED, products, HttpStatus.OK.getReasonPhrase());
    }

}
