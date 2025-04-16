package com.appservice.util;

import com.appservice.dto.EmployeeRequestDetailDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    public String getUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("Authentication is null");
            return Constants.DEFAULT_USER_ID;
        }

        System.out.println("Auth class: " + authentication.getClass().getName());

        Object principal = authentication.getPrincipal();

        if (principal instanceof EmployeeRequestDetailDto userDetails) {
            return userDetails.getId();
        }

        return Constants.DEFAULT_USER_ID;
    }
}
