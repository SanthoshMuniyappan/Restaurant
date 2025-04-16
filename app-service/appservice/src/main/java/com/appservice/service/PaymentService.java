package com.appservice.service;

import com.appservice.dto.PaymentDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.PaymentNotFoundException;
import com.appservice.repository.OrdersRepository;
import com.appservice.repository.PaymentRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Orders;
import ors.common.model.Payment;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrdersRepository ordersRepository;

    private final AuthenticationService authenticationService;

    public PaymentService(final PaymentRepository paymentRepository, final OrdersRepository ordersRepository, final AuthenticationService authenticationService) {
        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        final Orders orders = this.ordersRepository.findById(paymentDTO.getOrderId()).orElseThrow(() -> new PaymentNotFoundException(Constants.ORDER_ID_NOT_FOUND, "/api/v1/payment/create", authenticationService.getUserId()));
        payment.setOrder(orders);
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setStatus(paymentDTO.getStatus());
        payment.setAmount(paymentDTO.getAmount());
        payment.setCreatedBy(paymentDTO.getCreatedBy());
        payment.setUpdatedBy(paymentDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, payment, HttpStatus.CREATED.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Payment payment = this.paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(Constants.PAYMENT_ID_NOT_FOUND, "/api/v1/payment/update/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, payment, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.paymentRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException(Constants.PAYMENT_ID_NOT_FOUND, "api/v1/payment/remove/{id}", authenticationService.getUserId());
        }
        this.paymentRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
