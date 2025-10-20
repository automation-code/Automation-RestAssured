package com.api.automation.testingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    // ========== TEST DATA CONSTANTS ==========
    public static final String DEFAULT_BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String API_VERSION = "v1";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";

    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    public static final int BAD_REQUEST_STATUS_CODE = 400;
    public static final int UNAUTHORIZED_STATUS_CODE = 401;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    public static final int SERVER_ERROR_STATUS_CODE = 500;

    // ========== EXPECTED RESPONSE TIMES ==========
    public static final long FAST_RESPONSE_TIME = 1000;
    public static final long NORMAL_RESPONSE_TIME = 5000;
    public static final long SLOW_RESPONSE_TIME = 10000;

    // ========== USER PAYLOADS ==========
    public static Map<String, Object> getUserPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "John Doe");
        payload.put("email", "john@example.com");
        payload.put("phone", "1234567890");
        payload.put("active", true);
        return payload;
    }

    public static Map<String, Object> getUpdateUserPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Jane Doe");
        payload.put("email", "jane@example.com");
        payload.put("phone", "9876543210");
        return payload;
    }

    public static List<Map<String, Object>> getBulkUserPayload() {
        List<Map<String, Object>> users = new ArrayList<>();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("name", "User One");
        user1.put("email", "user1@test.com");
        users.add(user1);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("name", "User Two");
        user2.put("email", "user2@test.com");
        users.add(user2);

        return users;
    }

    // ========== ORDER PAYLOADS ==========
    public static Map<String, Object> getOrderPayload() {
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", "ORD-" + System.currentTimeMillis());
        order.put("customerId", "CUST-12345");
        order.put("totalAmount", 299.99);
        order.put("paymentMethod", "CREDIT_CARD");
        order.put("status", "PENDING");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("productId", "PROD001");
        item.put("quantity", 2);
        item.put("price", 149.99);
        items.add(item);

        order.put("items", items);
        return order;
    }

    public static Map<String, Object> getAddressPayload() {
        Map<String, Object> address = new HashMap<>();
        address.put("street", "123 Main St");
        address.put("city", "New York");
        address.put("state", "NY");
        address.put("zipCode", "10001");
        address.put("country", "USA");
        return address;
    }

    // ========== AUTHENTICATION DATA ==========
    public static Map<String, String> getAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer token123");
        headers.put("X-API-Key", "apikey123");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static Map<String, String> getBasicAuthHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic dGVzdDp0ZXN0");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    // ========== QUERY PARAMETERS ==========
    public static Map<String, String> getFilterParams() {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");
        params.put("sort", "id");
        params.put("order", "asc");
        return params;
    }

    public static Map<String, String> getSearchParams() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "john");
        params.put("category", "users");
        params.put("active", "true");
        return params;
    }

    // ========== INVALID DATA FOR ERROR TESTING ==========
    public static Map<String, Object> getInvalidUserPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "");  // Empty name
        payload.put("email", "invalid-email");  // Invalid email
        return payload;
    }

    public static Map<String, Object> getMissingRequiredFields() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "test@example.com");
        return payload;
    }

    // ========== NESTED PAYLOADS ==========
    public static Map<String, Object> getComplexUserPayload() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "John Doe");
        user.put("email", "john@example.com");
        user.put("verified", true);

        Map<String, Object> address = getAddressPayload();
        user.put("address", address);

        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Python");
        skills.add("Rest API");
        user.put("skills", skills);

        List<Map<String, String>> contacts = new ArrayList<>();
        Map<String, String> contact1 = new HashMap<>();
        contact1.put("type", "mobile");
        contact1.put("value", "1234567890");
        contacts.add(contact1);

        Map<String, String> contact2 = new HashMap<>();
        contact2.put("type", "home");
        contact2.put("value", "9876543210");
        contacts.add(contact2);

        user.put("contacts", contacts);
        return user;
    }
}

