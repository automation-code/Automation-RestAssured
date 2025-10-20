package com.api.automation.validation;

import io.restassured.response.Response;
import org.apache.log4j.Logger;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ResponseValidator {

    private static Logger logger = Logger.getLogger(ResponseValidator.class);
    private Response response;

    public ResponseValidator(Response response) {
        this.response = response;
    }

    public ResponseValidator validateStatusCode(int expectedCode) {
        assertThat("Status Code Mismatch", response.statusCode(), equalTo(expectedCode));
        logger.info("Status code validated: " + expectedCode);
        return this;
    }

    public ResponseValidator validateStatusCodeIsSuccess() {
        int statusCode = response.statusCode();
        assertThat("Status Code should be 2xx", statusCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
        logger.info("Status code is success (2xx)");
        return this;
    }

    public ResponseValidator validateStatusCodeIs4xx() {
        int statusCode = response.statusCode();
        assertThat("Status Code should be 4xx", statusCode, allOf(greaterThanOrEqualTo(400), lessThan(500)));
        logger.info("Status code is 4xx");
        return this;
    }

    public ResponseValidator validateStatusCodeIs5xx() {
        int statusCode = response.statusCode();
        assertThat("Status Code should be 5xx", statusCode, allOf(greaterThanOrEqualTo(500), lessThan(600)));
        logger.info("Status code is 5xx");
        return this;
    }

    public ResponseValidator validateJsonPath(String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertThat("JSON Path: " + jsonPath, actualValue, equalTo(expectedValue));
        logger.info("JSON path validated: " + jsonPath + " = " + expectedValue);
        return this;
    }

    public ResponseValidator validateJsonPathContains(String jsonPath, String value) {
        String actualValue = response.jsonPath().getString(jsonPath);
        assertThat("JSON Path: " + jsonPath, actualValue, containsString(value));
        logger.info("JSON path contains validated: " + jsonPath);
        return this;
    }

    public ResponseValidator validateResponseTime(long maxMillis) {
        long responseTime = response.time();
        assertThat("Response Time", responseTime, lessThanOrEqualTo(maxMillis));
        logger.info("Response time validated: " + responseTime + "ms");
        return this;
    }

    public ResponseValidator validateHeader(String headerName, String expectedValue) {
        assertThat("Header: " + headerName, response.header(headerName), equalTo(expectedValue));
        logger.info("Header validated: " + headerName);
        return this;
    }

    public ResponseValidator validateContentType(String contentType) {
        assertThat("Content-Type", response.contentType(), containsString(contentType));
        logger.info("Content-Type validated: " + contentType);
        return this;
    }

    public ResponseValidator validateBodyNotEmpty() {
        assertThat("Response Body", response.body().asString(), not(emptyOrNullString()));
        logger.info("Response body is not empty");
        return this;
    }

    public ResponseValidator validateJsonArray(String jsonPath, int expectedSize) {
        int actualSize = response.jsonPath().getList(jsonPath).size();
        assertThat("Array Size: " + jsonPath, actualSize, equalTo(expectedSize));
        logger.info("Array size validated: " + jsonPath + " = " + expectedSize);
        return this;
    }

    public Object extractJsonPath(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        logger.info("Extracted: " + jsonPath + " = " + value);
        return value;
    }
}
