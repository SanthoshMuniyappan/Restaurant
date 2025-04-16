package com.appservice.controller;

import com.appservice.dto.OrderItemsDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.service.OrderItemsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-items")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    public OrderItemsController(final OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final OrderItemsDTO orderItemsDTO) {
        return this.orderItemsService.createOrderItem(orderItemsDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final OrderItemsDTO orderItemsDTO) {
        return this.orderItemsService.updateOrderItem(id, orderItemsDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable("id") final String id) {
        return this.orderItemsService.retrieve(id);
    }

    @GetMapping("/retrieve")
    public ResponseDTO retrieveAll() {
        return this.orderItemsService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable("id") final String id) {
        return this.orderItemsService.remove(id);
    }

    @PutMapping("/kot/ready-status/{id}")
    public ResponseDTO UpdateKotReadyStatus(@PathVariable("id") final String id){
        return this.orderItemsService.UpdateKotReadyStatus(id);
    }

    @PutMapping("/server/delivered-status/{id}")
    public ResponseDTO UpdateServerStatus(@PathVariable("id") final String id,@RequestBody final OrderItemsDTO orderItemsDTO){
        return this.orderItemsService.UpdateServerDeliveredStatus(id,orderItemsDTO);
    }

    @PutMapping("/kot/prepare-status/{id}")
    public ResponseDTO UpdateKotPreparingStatus(@PathVariable("id") final String id){
        return this.orderItemsService.UpdateKotPreparingStatus(id);
    }
}
