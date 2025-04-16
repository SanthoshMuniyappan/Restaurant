package com.appservice.controller;

import com.appservice.dto.OrdersDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.OrdersService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(final OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/place-order")
    public ResponseDTO placeOrder(@RequestBody OrdersDTO ordersDTO) {
        return this.ordersService.placeOrder(ordersDTO);
    }

    @PutMapping("/order-approve/{id}")
    public ResponseDTO orderApprove(@PathVariable("id") String id) {
        return this.ordersService.orderApprove(id);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.ordersService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.ordersService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.ordersService.remove(id);
    }

    @PutMapping("/update-get-amount/{id}")
    public ResponseDTO updateAndGetAmount(@PathVariable("id")final String id){
        return this.ordersService.updateAndGetAmount(id);
    }
}
