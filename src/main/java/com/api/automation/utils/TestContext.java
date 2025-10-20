package com.api.automation.utils;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static Logger logger = Logger.getLogger(TestContext.class);
    private Response lastResponse;
    private Map<String, Object> contextData;
    private Map<String, String> extractedData;

    public TestContext() {
        this.contextData = new HashMap<>();
        this.extractedData = new HashMap<>();
        logger.debug("TestContext initialized");
    }

    public Response getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Response response) {
        this.lastResponse = response;
        logger.debug("Last response set");
    }

    public void setContextData(String key, Object value) {
        contextData.put(key, value);
        logger.debug("Context data set: " + key);
    }

    public Object getContextData(String key) {
        return contextData.get(key);
    }

    public void setExtractedData(String key, String value) {
        extractedData.put(key, value);
        logger.debug("Extracted data set: " + key + " = " + value);
    }

    public String getExtractedData(String key) {
        return extractedData.get(key);
    }

    public void clearContext() {
        contextData.clear();
        extractedData.clear();
        logger.debug("Context cleared");
    }
}
