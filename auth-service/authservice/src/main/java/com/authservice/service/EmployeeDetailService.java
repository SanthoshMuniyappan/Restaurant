package com.authservice.service;

import com.authservice.dto.EmployeeRequestDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ors.common.model.Employee;

@Component
public class EmployeeDetailService implements UserDetailsService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Employee employee= webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/employee/name/{name}",username)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();

        if (employee == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new EmployeeRequestDetailDto(employee);
    }
}
