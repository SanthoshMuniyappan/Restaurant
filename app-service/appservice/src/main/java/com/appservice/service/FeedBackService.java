package com.appservice.service;

import com.appservice.dto.FeedBackDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.CustomerNotFoundException;
import com.appservice.exception.FeedBackNotFoundException;
import com.appservice.repository.CustomerRepository;
import com.appservice.repository.FeedBackRepository;
import com.appservice.repository.RestaurantRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Customer;
import ors.common.model.FeedBack;
import ors.common.model.Restaurant;

@Service
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;

    private final RestaurantRepository restaurantRepository;

    private final AuthenticationService authenticationService;

    private final CustomerRepository customerRepository;

    public FeedBackService(final FeedBackRepository feedBackRepository, final RestaurantRepository restaurantRepository, final AuthenticationService authenticationService, final CustomerRepository customerRepository) {
        this.feedBackRepository = feedBackRepository;
        this.restaurantRepository = restaurantRepository;
        this.authenticationService = authenticationService;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ResponseDTO create(final FeedBackDTO feedBackDTO) {
        final FeedBack feedBack = new FeedBack();
        final Restaurant restaurant = this.restaurantRepository.findById(feedBackDTO.getRestaurantId()).orElseThrow(() -> new FeedBackNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/feedback/create", authenticationService.getUserId()));
        final Customer customer = this.customerRepository.findById(feedBackDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND, "api/v1/feedback/create", authenticationService.getUserId()));

        if (customer.getRestaurant().equals(restaurant)) {
            feedBack.setRestaurant(restaurant);
            feedBack.setCustomer(customer);
            feedBack.setRestaurantRating(feedBackDTO.getRestaurantRating());
            feedBack.setAppRating(feedBackDTO.getAppRating());
            feedBack.setComment(feedBackDTO.getComment());
            feedBack.setCreatedBy(authenticationService.getUserId());
            feedBack.setUpdatedBy(authenticationService.getUserId());
            return new ResponseDTO(Constants.CREATED, this.feedBackRepository.save(feedBack), HttpStatus.OK.getReasonPhrase());
        }else {
            throw new CustomerNotFoundException(Constants.CUSTOMER_NOT_CAPABLE_OF_THIS_RESTAURANT,"api/v1/feedback/create", authenticationService.getUserId());
        }
    }

    public ResponseDTO retrieve(final String id) {
        final FeedBack feedBack = this.feedBackRepository.findById(id).orElseThrow(() -> new FeedBackNotFoundException(Constants.FEEDBACK_ID_NOT_FOUND, "api/v1/feedback/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, feedBack, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.feedBackRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.feedBackRepository.existsById(id)) {
            throw new FeedBackNotFoundException(Constants.FEEDBACK_ID_NOT_FOUND, "api/v1/feedback/remove/{id}", authenticationService.getUserId());
        }
        this.feedBackRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

}
