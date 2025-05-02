package com.appservice.service;

import com.appservice.dto.OrdersDTO;
import com.appservice.dto.ResponseDTO;
import com.appservice.exception.CustomerNotFoundException;
import com.appservice.exception.EmployeeNotFoundException;
import com.appservice.exception.OrderNotFoundException;
import com.appservice.repository.CustomerRepository;
import com.appservice.repository.EmployeeRepository;
import com.appservice.repository.OrderItemRepository;
import com.appservice.repository.OrdersRepository;
import com.appservice.socket.SocketIOConnectionService;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Customer;
import ors.common.model.Employee;
import ors.common.model.OrderItems;
import ors.common.model.OrderStatus;
import ors.common.model.Orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final CustomerRepository customerRepository;

    private final OrderItemRepository orderItemRepository;

    private final EmployeeRepository employeeRepository;

    private final AuthenticationService authenticationService;

    private final SocketIOConnectionService socketIOConnectionService;

    public OrdersService(final EmployeeRepository employeeRepository, final OrdersRepository ordersRepository, final CustomerRepository customerRepository, final AuthenticationService authenticationService, final OrderItemRepository orderItemRepository,final SocketIOConnectionService socketIOConnectionService) {
        this.customerRepository = customerRepository;
        this.ordersRepository = ordersRepository;
        this.employeeRepository = employeeRepository;
        this.authenticationService = authenticationService;
        this.orderItemRepository = orderItemRepository;
        this.socketIOConnectionService=socketIOConnectionService;
    }

    @Transactional
    public ResponseDTO placeOrder(final OrdersDTO ordersDTO) {
        final Orders orders = new Orders();
        final Customer customer = this.customerRepository.findById(ordersDTO.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException(Constants.CUSTOMER_ID_NOT_FOUND, "api/v1/order/place-order", authenticationService.getUserId()));
        final Employee employee = this.employeeRepository.findById(ordersDTO.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException(Constants.EMPLOYEE_ID_NOT_FOUND, "api/v1/order/place-order", authenticationService.getUserId()));
        orders.setName(ordersDTO.getName());
        orders.setCustomer(customer);
        orders.setEmployee(employee);
        orders.setStatus(OrderStatus.PENDING);
        orders.setCreatedBy(authenticationService.getUserId());
        orders.setUpdatedBy(authenticationService.getUserId());
        return new ResponseDTO(Constants.CREATED, this.ordersRepository.save(orders), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO orderApprove(final String id) {
        final Orders orders = this.ordersRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND, "api/v1/order/order-approve/{id}", authenticationService.getUserId()));
        orders.setStatus(OrderStatus.APPROVED);

        Map<String, Object> data = new HashMap<>();
        data.put("message", "Order approved!");

        String customerId = orders.getCustomer().getId();
        socketIOConnectionService.sendMessageToCustomer(customerId, "order_approved", data);

        System.out.println(data.get("message"));

        return new ResponseDTO(Constants.UPDATED, this.ordersRepository.save(orders), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.ordersRepository.existsById(id)) {
            throw new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND, "api/v1/order/remove/{id}", authenticationService.getUserId());
        }
        this.ordersRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieve(final String id) {
        final Orders orders = this.ordersRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND, "api/v1/order/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, orders, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveAll() {
        return new ResponseDTO(Constants.RETRIEVED, this.ordersRepository.findAll(), HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO updateAndGetAmount(final String id) {
        final Orders orders = this.ordersRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_ID_NOT_FOUND, "api/v1/order/retrieve/{id}", authenticationService.getUserId()));
        List<OrderItems> orderItems = orderItemRepository.findAllByOrderId(id);
        int amount = orderItems.stream().mapToInt(OrderItems::getTotalPrice).sum();
        orders.setAmount(amount);
        return new ResponseDTO(Constants.RETRIEVED, amount, HttpStatus.OK.getReasonPhrase());
    }
}
