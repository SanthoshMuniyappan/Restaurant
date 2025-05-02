package com.appservice.socket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class SessionStore {

    private static final ConcurrentHashMap<String, UUID> customerSessionMap = new ConcurrentHashMap<>();

    public static void saveSession(String customerId, UUID sessionId) {
        customerSessionMap.put(customerId, sessionId);
    }

    public static UUID getSession(String customerId) {
        return customerSessionMap.get(customerId);
    }

    public static void removeSession(String customerId) {
        customerSessionMap.remove(customerId);
    }
}

