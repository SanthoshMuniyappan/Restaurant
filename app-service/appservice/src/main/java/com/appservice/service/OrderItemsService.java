package com.appservice.service;

import com.appservice.dto.OrderItemsDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.OrderItemsNotFoundException;
import com.appservice.exception.OrderNotFoundException;
import com.appservice.exception.ProductsNotFoundException;
import com.appservice.repository.OrderItemRepository;
import com.appservice.repository.OrdersRepository;
import com.appservice.repository.ProductsRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.OrderItemStatus;
import ors.common.model.OrderItems;
import ors.common.model.OrderStatus;
import ors.common.model.Orders;
import ors.common.model.Products;

import java.util.List;

@Service
public class OrderItemsService {

    private final OrderItemRepository orderItemRepository;

    private final OrdersRepository ordersRepository;

    private final ProductsRepository productsRepository;

    private final AuthenticationService authenticationService;

    public OrderItemsService(final OrderItemRepository orderItemRepository, final OrdersRepository ordersRepository, final ProductsRepository productsRepository, final AuthenticationService authenticationService) {
        this.orderItemRepository = orderItemRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public ResponseDTO createOrderItem(final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = new OrderItems();
        final OrderItemStatus status = OrderItemStatus.ORDERED;
        final Orders orders = this.ordersRepository.findById(orderItemsDTO.getOrderId()).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND, "api/v1/order-items/create", authenticationService.getUserId()));
        final Products products = this.productsRepository.findById(orderItemsDTO.getProductsId()).orElseThrow(() -> new ProductsNotFoundException(Constants.PRODUCT_ID_NOT_FOUND, "api/v1/order-items/create", authenticationService.getUserId()));
        if (orders.getStatus().equals(OrderStatus.APPROVED)) {
            orderItems.setOrder(orders);
        } else {
            throw new BadServiceAlertException(Constants.ORDER_NOT_APPROVED, "api/v1/order-items/create", authenticationService.getUserId());
        }
        orderItems.setQuantity(orderItemsDTO.getQuantity());
        orderItems.setSharedWithTable(orderItemsDTO.getSharedWithTable());
        orderItems.setSpecialInstruction(orderItemsDTO.getSpecialInstruction());
        orderItems.setStatus(status.getStatus());
        orderItems.setProducts(products);
        final int totalPrice = orderItemsDTO.getQuantity() * products.getPrice();
        orderItems.setTotalPrice(totalPrice);
        orderItems.setCreatedBy(authenticationService.getUserId());
        orderItems.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.orderItemRepository.save(orderItems), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateOrderItem(final String id, final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/update/{id}", authenticationService.getUserId()));

        if (orderItemsDTO.getQuantity() != 0) {
            orderItems.setQuantity(orderItemsDTO.getQuantity());
        }
        if (orderItemsDTO.getSpecialInstruction() != null) {
            orderItems.setSpecialInstruction(orderItemsDTO.getSpecialInstruction());
        }
        if (orderItemsDTO.getSharedWithTable() != null) {
            orderItems.setSharedWithTable(orderItemsDTO.getSharedWithTable());
        }
        return new ResponseDTO(Constants.UPDATED, this.orderItemRepository.save(orderItems), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, orderItems, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED,this.orderItemRepository.findAll() , HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.orderItemRepository.existsById(id)) {
            throw new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/remove/{id}", authenticationService.getUserId());
        }
        this.orderItemRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO UpdateKotReadyStatus(final String id) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/kot/ready-status/{id}", authenticationService.getUserId()));
        final OrderItemStatus status = OrderItemStatus.READY;
        orderItems.setStatus(status.getStatus());
        return new ResponseDTO(Constants.UPDATED, this.orderItemRepository.save(orderItems), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO UpdateServerDeliveredStatus(final String id, final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/server/delivered-status/{id}", authenticationService.getUserId()));
        final OrderItemStatus status = OrderItemStatus.DELIVERED;
        orderItems.setStatus(status.getStatus());
        return new ResponseDTO(Constants.UPDATED, this.orderItemRepository.save(orderItems), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO UpdateKotPreparingStatus(final String id) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND, "api/v1/order-items/kot/prepare-status/{id}", authenticationService.getUserId()));
        final OrderItemStatus status = OrderItemStatus.PREPARING;
        orderItems.setStatus(status.getStatus());
        return new ResponseDTO(Constants.UPDATED, this.orderItemRepository.save(orderItems), HttpStatus.OK.getReasonPhrase());
    }
}
