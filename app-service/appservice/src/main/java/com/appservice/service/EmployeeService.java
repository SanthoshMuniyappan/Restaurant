package com.appservice.service;

import com.appservice.dto.EmployeeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.EmployeeNotFoundException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.EmployeeRepository;
import com.appservice.repository.RestaurantRepository;
import com.appservice.util.Constants;
import com.appservice.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Employee;
import ors.common.model.Restaurant;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RestaurantRepository restaurantRepository;

    public EmployeeService(final EmployeeRepository employeeRepository, final RestaurantRepository restaurantRepository) {
        this.employeeRepository = employeeRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public ResponseDTO create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        final Restaurant restaurant = this.restaurantRepository.findById(employeeDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND));
        final Optional<Employee> employee1 = this.employeeRepository.findByEmail(employeeDTO.getEmail());
        employee.setName(employeeDTO.getName());
        employee.setRole(employeeDTO.getRole());
        employee.setRestaurant(restaurant);
        employee.setPassword(employeeDTO.getPassword());
        if (UtilService.emailValidation(employeeDTO.getEmail())) {
            if (employee1.isEmpty()) {
                employee.setEmail(employeeDTO.getEmail());
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST);
            }
        } else {
            throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
        }

        return new ResponseDTO(Constants.CREATED, this.employeeRepository.save(employee), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final EmployeeDTO employeeDTO) {
        final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND));
        final Restaurant restaurant = this.restaurantRepository.findById(employeeDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND));
        final Optional<Employee> employee1 = this.employeeRepository.findByEmail(employeeDTO.getEmail());
        if (employeeDTO.getName() != null) {
            employee.setName(employeeDTO.getName());
        }
        if (employeeDTO.getRole() != null) {
            employee.setRole(employeeDTO.getRole());
        }
        if (employeeDTO.getEmail() != null) {
            if (UtilService.emailValidation(employeeDTO.getEmail())) {
                if (employee1.isEmpty()) {
                    employee.setEmail(employeeDTO.getEmail());
                } else {
                    throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST);
                }
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN);
            }
        }
        return new ResponseDTO(Constants.UPDATED, this.employeeRepository.save(employee), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, employee, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.employeeRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.employeeRepository.existsById(id)) {
            this.employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
