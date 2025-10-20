package com.api.automation.pojo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class APIClient {

    private static Logger logger = Logger.getLogger(APIClient.class);
    private RequestSpecification requestSpec;
    private Response response;
    private Map<String, String> headers;
    private String baseURI;

    public APIClient(String baseURI) {
        this.baseURI = baseURI;
        this.headers = new HashMap<>();
        initializeRequest();
        logger.info("APIClient initialized with baseURI: " + baseURI);
    }

    private void initializeRequest() {
        RestAssured.baseURI = baseURI;
        requestSpec = RestAssured.given().contentType(ContentType.JSON);
    }

    public APIClient addHeader(String key, String value) {
        headers.put(key, value);
        requestSpec.header(key, value);
        logger.debug("Header added: " + key + " = " + value);
        return this;
    }

    public APIClient addHeaders(Map<String, String> headerMap) {
        headers.putAll(headerMap);
        requestSpec.headers(headerMap);
        logger.debug("Headers added: " + headerMap.size() + " headers");
        return this;
    }

    public APIClient setBasicAuth(String username, String password) {
        requestSpec.auth().basic(username, password);
        logger.debug("Basic authentication set for user: " + username);
        return this;
    }

    public APIClient setBearerToken(String token) {
        addHeader("Authorization", "Bearer " + token);
        logger.debug("Bearer token set");
        return this;
    }

    public APIClient setQueryParams(Map<String, String> queryParams) {
        requestSpec.queryParams(queryParams);
        logger.debug("Query parameters set: " + queryParams);
        return this;
    }

    public APIClient setQueryParam(String key, String value) {
        requestSpec.queryParam(key, value);
        logger.debug("Query parameter set: " + key + " = " + value);
        return this;
    }

    public APIClient setPathParam(String key, String value) {
        requestSpec.pathParam(key, value);
        logger.debug("Path parameter set: " + key + " = " + value);
        return this;
    }

    public APIClient setBody(Object body) {
        requestSpec.body(body);
        logger.debug("Request body set");
        return this;
    }

    public APIClient setContentType(ContentType contentType) {
        requestSpec.contentType(contentType);
        logger.debug("Content-Type set to: " + contentType);
        return this;
    }

    public Response get(String endpoint) {
        logger.info("Sending GET request to: " + endpoint);
        response = requestSpec.get(endpoint);
        logger.info("GET Response Status: " + response.statusCode());
        return response;
    }

    public Response post(String endpoint) {
        logger.info("Sending POST request to: " + endpoint);
        response = requestSpec.post(endpoint);
        logger.info("POST Response Status: " + response.statusCode());
        return response;
    }

    public Response put(String endpoint) {
        logger.info("Sending PUT request to: " + endpoint);
        response = requestSpec.put(endpoint);
        logger.info("PUT Response Status: " + response.statusCode());
        return response;
    }

    public Response patch(String endpoint) {
        logger.info("Sending PATCH request to: " + endpoint);
        response = requestSpec.patch(endpoint);
        logger.info("PATCH Response Status: " + response.statusCode());
        return response;
    }

    public Response delete(String endpoint) {
        logger.info("Sending DELETE request to: " + endpoint);
        response = requestSpec.delete(endpoint);
        logger.info("DELETE Response Status: " + response.statusCode());
        return response;
    }

    public Response request(Method method, String endpoint) {
        logger.info("Sending " + method + " request to: " + endpoint);
        response = requestSpec.request(method, endpoint);
        logger.info("Response Status: " + response.statusCode());
        return response;
    }

    public Response getResponse() {
        return response;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public String getResponseBody() {
        return response.body().asString();
    }

    public String getHeader(String headerName) {
        return response.header(headerName);
    }

    public String getJsonPath(String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    public void resetRequest() {
        initializeRequest();
        logger.debug("Request reset");
    }
}
