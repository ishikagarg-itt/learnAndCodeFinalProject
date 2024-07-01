package org.example.utils;

import org.example.Entity.User;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationUtils {
    private static final Map<String, String> sessionMap = new ConcurrentHashMap<>();
    public static String generateSessionToken(User user) {
        String sessionToken = UUID.randomUUID().toString() + "|" + user.getUserName() + "|" + user.getRole().getName();
        sessionMap.put(sessionToken, user.getUserName());
        return sessionToken;
    }

    public static boolean isValidSessionToken(String sessionToken) {
        return sessionMap.containsKey(sessionToken);
    }

    public static String getRoleFromToken(String sessionToken){
        String[] sessionTokenParts = sessionToken.split("\\|");
        return sessionTokenParts[2];
    }

    public static String getSession(String sessionToken){
        return sessionMap.get(sessionToken);
    }
}
