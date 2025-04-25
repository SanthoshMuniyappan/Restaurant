package com.appservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RealTimeNotificationService {

    @Value("${socketio.server.url}")
    private String socketIoServerUrl;

    private final RestTemplate restTemplate;

    public RealTimeNotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyOrderApproval(String customerId, String orderId) {
        String url = UriComponentsBuilder.fromHttpUrl(socketIoServerUrl + "/approveOrder")
                .queryParam("customerId", customerId)
                .queryParam("orderId", orderId)
                .toUriString();

        restTemplate.postForObject(url, null, String.class);
    }

    public void notifyOrderStatus(String customerId, String orderId, String status) {
        String url = UriComponentsBuilder.fromHttpUrl(socketIoServerUrl + "/orderStatusUpdate")
                .queryParam("customerId", customerId)
                .queryParam("orderId", orderId)
                .queryParam("status", status)
                .toUriString();

        restTemplate.postForObject(url, null, String.class);
    }
}

