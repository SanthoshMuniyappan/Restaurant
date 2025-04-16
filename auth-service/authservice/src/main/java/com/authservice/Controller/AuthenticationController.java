package com.authservice.Controller;

import com.authservice.dto.EmployeeSignInDTO;
import com.authservice.dto.ResponseDTO;
import com.authservice.service.JwtService;
import com.authservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationController(final AuthenticationManager authenticationManager, final JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/authenticate")
    public ResponseDTO authenticate(@RequestBody final EmployeeSignInDTO employeeSignInDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeSignInDTO.getName(), employeeSignInDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = this.jwtService.generateToken(employeeSignInDTO.getName());
            return new ResponseDTO(Constants.TOKEN, token, HttpStatus.OK.getReasonPhrase());
        } else {
            throw new UsernameNotFoundException(Constants.EMPLOYEE_NOT_FOUND);
        }
    }

}
