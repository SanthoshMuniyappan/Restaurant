package com.appservice.service;

import com.appservice.dto.EmployeeRequestDetailDto;
import com.appservice.repository.EmployeeRepository;
import com.appservice.util.Constants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ors.common.model.Employee;

@Component
public class EmployeeDetailService implements UserDetailsService {


    private final EmployeeRepository employeeRepository;

    public EmployeeDetailService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final Employee employee = employeeRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.EMPLOYEE_NOT_FOUND + name));


        return new EmployeeRequestDetailDto(employee);
    }
}
