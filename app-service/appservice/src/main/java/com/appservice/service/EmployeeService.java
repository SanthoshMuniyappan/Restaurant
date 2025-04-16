package com.appservice.service;

import com.appservice.dto.EmployeeDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.EmployeeNotFoundException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.EmployeeRepository;
import com.appservice.repository.RestaurantRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import com.appservice.util.UtilService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ors.common.model.Employee;
import ors.common.model.Restaurant;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RestaurantRepository restaurantRepository;

    private final AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeService(final EmployeeRepository employeeRepository, final RestaurantRepository restaurantRepository, final AuthenticationService authenticationService) {
        this.employeeRepository = employeeRepository;
        this.restaurantRepository = restaurantRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        final Restaurant restaurant = this.restaurantRepository.findById(employeeDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/employee/create", authenticationService.getUserId()));
        final Optional<Employee> employee1 = this.employeeRepository.findByEmail(employeeDTO.getEmail());
        employee.setName(employeeDTO.getName());
        employee.setRole(employeeDTO.getRole());
        employee.setRestaurant(restaurant);
        employee.setPassword(this.passwordEncoder.encode(employeeDTO.getPassword()));
        if (UtilService.emailValidation(employeeDTO.getEmail())) {
            if (employee1.isEmpty()) {
                employee.setEmail(employeeDTO.getEmail());
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST, "api/v1/employee/create", authenticationService.getUserId());
            }
        } else {
            throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN, "api/v1/employee/create", authenticationService.getUserId());
        }
        employee.setCreatedBy(authenticationService.getUserId());
        employee.setUpdatedBy(authenticationService.getUserId());

        return new ResponseDTO(Constants.CREATED, this.employeeRepository.save(employee), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO update(final String id, final EmployeeDTO employeeDTO) {
        final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND, "api/v1/employee/update/{id}", authenticationService.getUserId()));
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
                    throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST, "api/v1/employee/update/{id}", authenticationService.getUserId());
                }
            } else {
                throw new BadServiceAlertException(Constants.EMAIL_NOT_VALID + Constants.EMAIL_PATTERN, "api/v1/employee/update/{id}", authenticationService.getUserId());
            }
        }
        employee.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.UPDATED, this.employeeRepository.save(employee), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND, "api/v1/employee/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, employee, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.employeeRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND, "api/v1/employee/remove/{id}", authenticationService.getUserId());
        }
        this.employeeRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public Employee retrieveEmployee(final String name) {
        return this.employeeRepository.findByName(name).orElseThrow(() -> new EmployeeNotFoundException(Constants.CATEGORY_ID_NOT_FOUND, "api/v1/employee/name/{name}", authenticationService.getUserId()));
    }
}
