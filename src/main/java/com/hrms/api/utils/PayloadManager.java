package com.hrms.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public final class PayloadManager {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private PayloadManager() {
        // Utility class
    }

    public static Map<String, Object> createAuthPayload(String username, String password) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        return payload;
    }

    public static Map<String, Object> createEmployeePayload(int employeeId,
                                                            String firstName,
                                                            String lastName,
                                                            String email,
                                                            String mobile,
                                                            String department,
                                                            String designation,
                                                            int salary) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("employeeId", employeeId);
        payload.put("firstName", firstName);
        payload.put("lastName", lastName);
        payload.put("email", email);
        payload.put("mobile", mobile);
        payload.put("department", department);
        payload.put("designation", designation);
        payload.put("salary", salary);
        return payload;
    }

    public static String toJson(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to serialize payload to JSON", exception);
        }
    }
}
