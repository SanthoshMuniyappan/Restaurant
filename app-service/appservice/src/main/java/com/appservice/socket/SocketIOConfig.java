package com.appservice.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

    @Value("${socket.host}")
    private String socketHost;

    @Value("${socket.port}")
    private int socketPort;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(socketHost);
        config.setPort(socketPort);

        SocketIOServer server = new SocketIOServer(config);
        server.start();  // Important: Start server

        return server;
    }
}

