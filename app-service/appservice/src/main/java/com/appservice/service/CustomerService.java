package com.appservice.service;

import com.appservice.dto.CustomerDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.CustomerNotFoundException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.CustomerRepository;
import com.appservice.repository.RestaurantRepository;
import com.appservice.util.Constants;
import com.appservice.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Customer;
import ors.common.model.Restaurant;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    public CustomerService(final CustomerRepository customerRepository, final RestaurantRepository restaurantRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public ResponseDTO create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        final Restaurant restaurant = this.restaurantRepository.findById(customerDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND));
        final Optional<Customer> customer1 = this.customerRepository.findByEmail(customerDTO.getEmail());
        customer.setName(customerDTO.getName());
        if (UtilService.emailValidation(customerDTO.getEmail())) {
            if (customer1.isEmpty()) {
                customer.setEmail(customerDTO.getEmail());
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST);
            }
        } else {
            throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
        }
        customer.setPassword(customerDTO.getPassword());
        customer.setRestaurant(restaurant);
        customer.setCreatedBy(customerDTO.getCreatedBy());
        customer.setUpdatedBy(customerDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.customerRepository.save(customer), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final CustomerDTO customerDTO) {
        final Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND));
        final Optional<Customer> customer1 = this.customerRepository.findByEmail(customerDTO.getEmail());
        if (customerDTO.getName() != null) {
            customer.setName(customerDTO.getName());
        }
        if (customerDTO.getEmail() != null) {
            if (UtilService.emailValidation(customerDTO.getEmail())) {
                if (customer1.isEmpty()) {
                    customer.setEmail(customerDTO.getEmail());
                } else {
                    throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST);
                }
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
            }
        }
        if (customerDTO.getPassword() != null) {
            customer.setPassword(customerDTO.getPassword());
        }
        return new ResponseDTO(Constants.UPDATED, this.customerRepository.save(customer), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, customer, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.customerRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.customerRepository.existsById(id)) {
            this.customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
