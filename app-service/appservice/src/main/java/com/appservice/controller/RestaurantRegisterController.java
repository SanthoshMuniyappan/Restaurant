package com.appservice.controller;

import com.appservice.dto.ChangePasswordDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.dto.RestaurantLoginDTO;
import com.appservice.dto.RestaurantRegistrationDTO;
import com.appservice.dto.VerificationCodeDTO;
import com.appservice.service.RestaurantRegisterService;
import com.appservice.util.Constants;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant/register")
public class RestaurantRegisterController {

    private final RestaurantRegisterService restaurantRegisterService;

    public RestaurantRegisterController(final RestaurantRegisterService restaurantRegisterService) {
        this.restaurantRegisterService = restaurantRegisterService;
    }

    @PostMapping("/send-code")
    public ResponseDTO sendVerificationCode(@RequestParam("email") String email) throws MessagingException {
        restaurantRegisterService.sendVerificationCode(email);
        return new ResponseDTO(Constants.EMAIL_CODE_SEND, email, HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/verify-code")
    public ResponseDTO verifyCode(@RequestBody VerificationCodeDTO dto) throws MessagingException {
        restaurantRegisterService.verifyCodeAndSendTempPassword(dto.getEmail(), dto.getCode());
        return new ResponseDTO(Constants.TEMPORARY_PASSWORD_SEND, null, HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/register")
    public ResponseDTO register(@RequestBody RestaurantRegistrationDTO restaurantRegistrationDTO) {
        restaurantRegisterService.completeRegistration(restaurantRegistrationDTO);
        return new ResponseDTO(Constants.REGISTRATION_COMPLETE, null, HttpStatus.OK.getReasonPhrase());
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody RestaurantLoginDTO restaurantLoginDTO) {
        return this.restaurantRegisterService.login(restaurantLoginDTO);
    }

    @PutMapping("/change-password")
    public ResponseDTO changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        restaurantRegisterService.changePassword(changePasswordDTO);
        return new ResponseDTO(Constants.PASSWORD_UPDATED, null, HttpStatus.OK.getReasonPhrase());
    }
}
