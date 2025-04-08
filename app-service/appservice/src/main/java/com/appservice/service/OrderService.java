package com.appservice.service;

import com.appservice.dto.OrderDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.CustomerNotFoundException;
import com.appservice.exception.EmployeeNotFoundException;
import com.appservice.exception.OrderNotFoundException;
import com.appservice.repository.CustomerRepository;
import com.appservice.repository.EmployeeRepository;
import com.appservice.repository.OrderRepository;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Customer;
import ors.common.model.Employee;
import ors.common.model.Order;
import ors.common.model.OrderStatus;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    public OrderService(final EmployeeRepository employeeRepository, final OrderRepository orderRepository, final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public ResponseDTO placeOrder(final OrderDTO orderDTO) {
        final Order order = new Order();
        final Customer customer = this.customerRepository.findById(orderDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND));
        final Employee employee = this.employeeRepository.findById(orderDTO.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND));
        order.setName(orderDTO.getName());
        order.setCustomer(customer);
        order.setEmployee(employee);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedBy(orderDTO.getCreatedBy());
        order.setUpdatedBy(orderDTO.getUpdatedBy());
        return new ResponseDTO(Constants.CREATED, this.orderRepository.save(order), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO orderApprove(final String id) {
        final Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND));
        order.setStatus(OrderStatus.APPROVED);
        return new ResponseDTO(Constants.UPDATED, this.orderRepository.save(order), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (this.orderRepository.existsById(id)) {
            this.orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND);
        }
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Order order = this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, order, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.orderRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }
}
