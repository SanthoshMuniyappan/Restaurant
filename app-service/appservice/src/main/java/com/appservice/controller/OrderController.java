package com.appservice.controller;

import com.appservice.dto.OrderDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.OrderService;
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
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public ResponseDTO placeOrder(@RequestBody OrderDTO orderDTO) {
        return this.orderService.placeOrder(orderDTO);
    }

    @PutMapping("/order-approve/{id}")
    public ResponseDTO orderApprove(@PathVariable("id") String id) {
        return this.orderService.orderApprove(id);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.orderService.retrieve(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.orderService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.orderService.remove(id);
    }
}
