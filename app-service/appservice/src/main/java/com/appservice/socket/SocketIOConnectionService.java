package com.appservice.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SocketIOConnectionService {

    private static final Logger log = LoggerFactory.getLogger(SocketIOConnectionService.class);

    private final SocketIOServer server;
    private SocketIONamespace customerNamespace;
    private SocketIONamespace kotNamespace;


    public SocketIOConnectionService(SocketIOServer socketIOServer) {
        this.server = socketIOServer;
    }

    @PostConstruct
    public void init() {
        this.customerNamespace = server.addNamespace("/customer");
        this.kotNamespace = server.addNamespace("/kot"); // Add KOT namespace
        registerConnectionListeners();
        registerKOTListeners();
    }

    public void registerConnectionListeners() {
        customerNamespace.addConnectListener(client -> {
            if (client == null || client.getHandshakeData() == null) {
                return;
            }
            String customerId = client.getHandshakeData().getSingleUrlParam("customerId");
            if (customerId != null) {
                SessionStore.saveSession(customerId, client.getSessionId());
                log.info("Customer connected: {} with session: {}", customerId, client.getSessionId());
            }
        });

        customerNamespace.addDisconnectListener(client -> {
            if (client == null || client.getHandshakeData() == null) {
                return;
            }
            String customerId = client.getHandshakeData().getSingleUrlParam("customerId");
            if (customerId != null) {
                SessionStore.removeSession(customerId);
                log.info("Customer disconnected: {}", customerId);
            }
        });
    }

    public void sendMessageToCustomer(String customerId, String eventName, Object data) {
        UUID sessionId = SessionStore.getSession(customerId);
        if (sessionId != null) {
            SocketIOClient client = customerNamespace.getClient(sessionId);
            if (client != null) {
                client.sendEvent(eventName, data);
                log.info("Message sent to customer {}: {}", customerId, eventName);
            } else {
                log.warn("Client not found for customerId: {}", customerId);
            }
        } else {
            log.warn("Session not found for customerId: {}", customerId);
        }
    }

    //kot message receive like new order placed

    public void registerKOTListeners() {
        kotNamespace.addConnectListener(client -> {
            log.info("KOT connected with session ID: {}", client.getSessionId());
        });

        kotNamespace.addDisconnectListener(client -> {
            log.info("KOT disconnected with session ID: {}", client.getSessionId());
        });
    }

    public void broadcastToAllKOTs(String eventName, Object data) {
        for (SocketIOClient client : kotNamespace.getAllClients()) {
            client.sendEvent(eventName, data);
            log.info("Broadcast '{}' to KOT session: {}", eventName, client.getSessionId());
        }
    }

    @PreDestroy
    public void stopSocketServer() {
        if (this.server != null) {
            this.server.stop();
        }
    }
}
