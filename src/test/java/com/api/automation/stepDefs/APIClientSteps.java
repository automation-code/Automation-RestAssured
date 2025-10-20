package com.api.automation.stepDefs;

import com.api.automation.pojo.APIClient;
import com.api.automation.utils.TestContext;
import com.api.automation.validation.ResponseValidator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.Map;

public class APIClientSteps {

    private static Logger logger = Logger.getLogger(APIClientSteps.class);
    private APIClient apiClient;
    private TestContext testContext;
    private ResponseValidator validator;

    public APIClientSteps(SharedDriver sharedDriver) {
        this.apiClient = sharedDriver.getApiClient();
        this.testContext = sharedDriver.getTestContext();
    }

    @Given("Base URL is set to {string}")
    public void setBaseURL(String baseURL) {
        apiClient = new APIClient(baseURL);
        logger.info("Base URL set to: " + baseURL);
    }

    @Given("Clear all context data")
    public void clearContext() {
        testContext.clearContext();
        logger.info("Context cleared");
    }

    @When("I send GET request to {string}")
    public void sendGETRequest(String endpoint) {
        logger.info("Sending GET to: " + endpoint);
        Response response = apiClient.get(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I send POST request to {string}")
    public void sendPOSTRequest(String endpoint) {
        logger.info("Sending POST to: " + endpoint);
        Response response = apiClient.post(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I send PUT request to {string}")
    public void sendPUTRequest(String endpoint) {
        logger.info("Sending PUT to: " + endpoint);
        Response response = apiClient.put(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I send PATCH request to {string}")
    public void sendPATCHRequest(String endpoint) {
        logger.info("Sending PATCH to: " + endpoint);
        Response response = apiClient.patch(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I send DELETE request to {string}")
    public void sendDELETERequest(String endpoint) {
        logger.info("Sending DELETE to: " + endpoint);
        Response response = apiClient.delete(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I send GET request to {string} with query parameters:")
    public void sendGETWithQueryParams(String endpoint, DataTable dataTable) {
        Map<String, String> queryParams = dataTable.asMap();
        logger.info("Sending GET with query params");
        apiClient.setQueryParams(queryParams);
        Response response = apiClient.get(endpoint);
        testContext.setLastResponse(response);
    }

    @When("I add header {string} with value {string}")
    public void addHeader(String key, String value) {
        logger.info("Adding header: " + key);
        apiClient.addHeader(key, value);
    }

    @When("I add basic authentication with username {string} and password {string}")
    public void addBasicAuth(String username, String password) {
        logger.info("Adding basic auth");
        apiClient.setBasicAuth(username, password);
    }

    @When("I add bearer token {string}")
    public void addBearerToken(String token) {
        logger.info("Adding bearer token");
        apiClient.setBearerToken(token);
    }

    @When("I prepare request body with following data:")
    public void prepareRequestBody(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap();
        logger.info("Preparing request body");
        com.google.gson.Gson gson = new com.google.gson.Gson();
        String payload = gson.toJson(data);
        apiClient.setBody(payload);
    }

    @Then("Response status code should be {int}")
    public void validateStatusCode(int expectedCode) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateStatusCode(expectedCode);
    }

    @Then("Response status code should be 2xx")
    public void validateStatusCode2xx() {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateStatusCodeIsSuccess();
    }

    @Then("Response status code should be 4xx")
    public void validateStatusCode4xx() {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateStatusCodeIs4xx();
    }

    @Then("Response status code should be 5xx")
    public void validateStatusCode5xx() {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateStatusCodeIs5xx();
    }

    @Then("Response content type should be {string}")
    public void validateContentType(String contentType) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateContentType(contentType);
    }

    @Then("Response body should not be empty")
    public void validateBodyNotEmpty() {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateBodyNotEmpty();
    }

    @Then("JSON path {string} should equal to {string}")
    public void validateJsonPath(String jsonPath, String expectedValue) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateJsonPath(jsonPath, expectedValue);
    }

    @Then("JSON path {string} should equal to {int}")
    public void validateJsonPathInt(String jsonPath, int expectedValue) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateJsonPath(jsonPath, expectedValue);
    }

    @Then("JSON path {string} should contain {string}")
    public void validateJsonPathContains(String jsonPath, String value) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateJsonPathContains(jsonPath, value);
    }

    @Then("Response time should be less than {int} milliseconds")
    public void validateResponseTime(long maxMillis) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateResponseTime(maxMillis);
    }

    @Then("Response header {string} should contain {string}")
    public void validateResponseHeader(String headerName, String expectedValue) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateHeader(headerName, expectedValue);
    }

    @Then("JSON path {string} should have array size of {int}")
    public void validateArraySize(String jsonPath, int expectedSize) {
        validator = new ResponseValidator(testContext.getLastResponse());
        validator.validateJsonArray(jsonPath, expectedSize);
    }

    @When("I extract JSON path {string} as {string}")
    public void extractJsonPath(String jsonPath, String key) {
        validator = new ResponseValidator(testContext.getLastResponse());
        Object value = validator.extractJsonPath(jsonPath);
        testContext.setExtractedData(key, value.toString());
        logger.info("Extracted: " + key + " = " + value);
    }
}
