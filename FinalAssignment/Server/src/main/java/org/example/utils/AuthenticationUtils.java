package org.example.utils;

import org.example.Entity.User;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationUtils {
    private static final Map<String, String> sessionMap = new ConcurrentHashMap<>();
    public static String generateSessionToken(User user) {
        String sessionToken = UUID.randomUUID().toString();
        sessionMap.put(sessionToken, user.getEmployeeId());
        return sessionToken + "|" + user.getEmployeeId() + "|" + user.getRole().getName();
    }

    public static boolean isValidSessionToken(String sessionToken) {
        return sessionMap.containsKey(sessionToken);
    }
}
