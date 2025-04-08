package com.appservice.service;

import com.appservice.dto.OrderItemsDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.EmployeeNotFoundException;
import com.appservice.exception.OrderItemsNotFoundException;
import com.appservice.exception.OrderNotFoundException;
import com.appservice.exception.ProductsNotFoundException;
import com.appservice.repository.EmployeeRepository;
import com.appservice.repository.OrderItemRepository;
import com.appservice.repository.OrderRepository;
import com.appservice.repository.ProductsRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Employee;
import ors.common.model.Order;
import ors.common.model.OrderItemStatus;
import ors.common.model.OrderItems;
import ors.common.model.Products;

@Service
public class OrderItemsService {

    private final OrderItemRepository orderItemRepository;

    private final OrderRepository orderRepository;

    private final ProductsRepository productsRepository;

    private final EmployeeRepository employeeRepository;

    public OrderItemsService(final OrderItemRepository orderItemRepository, final OrderRepository orderRepository, final ProductsRepository productsRepository ,final EmployeeRepository employeeRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productsRepository = productsRepository;
        this.employeeRepository=employeeRepository;
    }

    @Transactional
    public ResponseDTO createOrderItem(final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = new OrderItems();
        final Order order = this.orderRepository.findById(orderItemsDTO.getOrderId()).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND));
        final Products products = this.productsRepository.findById(orderItemsDTO.getProductsId()).orElseThrow(() -> new ProductsNotFoundException(Constants.PRODUCT_ID_NOT_FOUND));
        final Employee employee=this.employeeRepository.findById(orderItemsDTO.getEmployeeId()).orElseThrow(()->new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND));
        orderItems.setQuantity(orderItemsDTO.getQuantity());
        orderItems.setSharedWithTable(orderItemsDTO.getSharedWithTable());
        orderItems.setSpecialInstruction(orderItemsDTO.getSpecialInstruction());
        orderItems.setEmployee(employee);
        orderItems.setOrder(order);
        orderItems.setProducts(products);
        orderItems.setCreatedBy(orderItemsDTO.getCreatedBy());
        orderItems.setUpdatedBy(orderItems.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.orderItemRepository.save(orderItems), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateOrderItem(final String id, final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND));

        if (orderItemsDTO.getQuantity() != null) {
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
        final OrderItems orderItems = this.orderItemRepository.findById(id).orElseThrow(() -> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, orderItems, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.orderItemRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.orderItemRepository.existsById(id)) {
            this.orderItemRepository.deleteById(id);
        } else {
            throw new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO UpdateKotStatus(final String id){
        final OrderItems orderItems=this.orderItemRepository.findById(id).orElseThrow(()-> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND));
        orderItems.setStatus(OrderItemStatus.READY);
        return  new ResponseDTO(Constants.UPDATED,this.orderItemRepository.save(orderItems),HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO UpdateServerStatus(final String id,final OrderItemsDTO orderItemsDTO){
        final OrderItems orderItems=this.orderItemRepository.findById(id).orElseThrow(()-> new OrderItemsNotFoundException(Constants.ORDER_ITEMS_ID_NOT_FOUND));
        orderItems.setStatus(orderItemsDTO.getStatus());
        return  new ResponseDTO(Constants.UPDATED,this.orderItemRepository.save(orderItems),HttpStatus.OK.getReasonPhrase());
    }
}
