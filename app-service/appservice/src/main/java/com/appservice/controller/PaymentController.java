package com.appservice.controller;

import com.appservice.dto.PaymentDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.PaymentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService){
        this.paymentService=paymentService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final PaymentDTO paymentDTO) {
        return this.paymentService.create(paymentDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.paymentService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.paymentService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.paymentService.remove(id);
    }
}
