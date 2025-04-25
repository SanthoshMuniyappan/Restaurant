package com.appservice.service;

import com.appservice.dto.ChangePasswordDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.dto.RestaurantLoginDTO;
import com.appservice.dto.RestaurantRegistrationDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.repository.RestaurantRegisterRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ors.common.model.RestaurantRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RestaurantRegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RestaurantRegisterRepository restaurantRegisterRepository;

    private final EmailService emailService;

    private final AuthenticationService authenticationService;

    private final Map<String, String> verificationCodes = new HashMap<>();
    private final Map<String, String> tempPasswords = new HashMap<>();

    public RestaurantRegisterService(final RestaurantRegisterRepository restaurantRegisterRepository, final EmailService emailService, final AuthenticationService authenticationService) {
        this.restaurantRegisterRepository = restaurantRegisterRepository;
        this.authenticationService = authenticationService;
        this.emailService = emailService;
    }

    public void sendVerificationCode(String email) throws MessagingException {
        if (restaurantRegisterRepository.existsByEmail(email)) {
            throw new BadServiceAlertException(Constants.EMAIL_ALREADY_REGISTERED, "/restaurant/register/send-code", authenticationService.getUserId());
        }

        String code = generate6DigitCode();
        verificationCodes.put(email, code);
        emailService.sendEmail(email, "Verification Code", "Your verification code is: " + code);
    }

    public void verifyCodeAndSendTempPassword(String email, String code) throws MessagingException {
        String storedCode = verificationCodes.get(email);
        if (storedCode == null || !storedCode.equals(code)) {
            throw new BadServiceAlertException(Constants.INVALID_VERIFICATION_CODE, "/restaurant/register/verify-code", authenticationService.getUserId());
        }

        String tempPassword = generateTempPassword();
        tempPasswords.put(email, tempPassword);
        emailService.sendEmail(email, "Temporary Password", "Your temporary password is: " + tempPassword);
        verificationCodes.remove(email);
    }

    public void completeRegistration(RestaurantRegistrationDTO restaurantRegistrationDTO) {
        if (restaurantRegisterRepository.existsByEmail(restaurantRegistrationDTO.getEmail())) {
            throw new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST, "/restaurant/register/register", authenticationService.getUserId());
        }

        String tempPass = tempPasswords.get(restaurantRegistrationDTO.getEmail());
        if (tempPass == null || !tempPass.equals(restaurantRegistrationDTO.getPassword())) {
            throw new BadServiceAlertException(Constants.TEMPORARY_PASSWORD_INVALID, "/restaurant/register/register", authenticationService.getUserId());
        }

        final RestaurantRegistration restaurantRegistration = new RestaurantRegistration();
        restaurantRegistration.setEmail(restaurantRegistrationDTO.getEmail());
        restaurantRegistration.setName(restaurantRegistrationDTO.getName());
        restaurantRegistration.setPassword(passwordEncoder.encode(tempPass));
        restaurantRegisterRepository.save(restaurantRegistration);

        tempPasswords.remove(restaurantRegistrationDTO.getEmail());
    }

    public ResponseDTO login(RestaurantLoginDTO restaurantLoginDTO) {
        final RestaurantRegistration restaurantRegistration = restaurantRegisterRepository.findByEmail(restaurantLoginDTO.getEmail())
                .orElseThrow(() -> new BadServiceAlertException(Constants.EMAIL_NOT_VALID, "/restaurant/register/login", authenticationService.getUserId()));

        if (!passwordEncoder.matches(restaurantLoginDTO.getPassword(), restaurantRegistration.getPassword())) {
            throw new BadServiceAlertException();
        }

        return new ResponseDTO(Constants.RESTAURANT_LOGIN, null, HttpStatus.OK.getReasonPhrase());
    }


    public void changePassword(ChangePasswordDTO dto) {
        final RestaurantRegistration restaurantRegistration = restaurantRegisterRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadServiceAlertException(Constants.EMAIL_ALREADY_EXIST, "/restaurant/register/change-password", authenticationService.getUserId()));

        restaurantRegistration.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        restaurantRegisterRepository.save(restaurantRegistration);
    }

    private String generate6DigitCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
