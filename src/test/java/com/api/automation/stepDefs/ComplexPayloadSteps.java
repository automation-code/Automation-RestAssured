package com.api.automation.stepDefs;

import com.api.automation.payload.ComplexPayloadBuilder;
import com.api.automation.payload.PayloadProcessor;
import com.api.automation.pojo.APIClient;
import com.api.automation.utils.DataGenerator;
import com.api.automation.utils.FileUtils;
import com.api.automation.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexPayloadSteps {

    private static Logger logger = Logger.getLogger(ComplexPayloadSteps.class);
    private APIClient apiClient;
    private TestContext testContext;
    private ComplexPayloadBuilder payloadBuilder;
    private PayloadProcessor payloadProcessor;

    public ComplexPayloadSteps(SharedDriver sharedDriver) {
        this.apiClient = sharedDriver.getApiClient();
        this.testContext = sharedDriver.getTestContext();
        this.payloadBuilder = new ComplexPayloadBuilder();
        this.payloadProcessor = new PayloadProcessor();
    }

    @When("I load JSON payload from file {string}")
    public void loadJsonPayloadFromFile(String fileName) {
        logger.info("Loading JSON from file: " + fileName);
        String payload = FileUtils.readJsonFile(fileName);
        apiClient.setBody(payload);
        testContext.setContextData("lastPayload", payload);
        payloadProcessor.logPayload(payload);
    }

    @When("I load JSON payload from file {string} with replacements:")
    public void loadJsonPayloadWithReplacements(String fileName, DataTable dataTable) {
        logger.info("Loading JSON with replacements: " + fileName);
        Map<String, String> replacements = dataTable.asMap();

        String[] replacementArray = new String[replacements.size() * 2];
        int index = 0;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            replacementArray[index++] = entry.getKey();
            replacementArray[index++] = entry.getValue();
        }

        String payload = FileUtils.readJsonFileWithReplacements(fileName, replacementArray);
        apiClient.setBody(payload);
        testContext.setContextData("lastPayload", payload);
        payloadProcessor.logPayload(payload);
    }

    @When("I load XML payload from file {string}")
    public void loadXmlPayloadFromFile(String fileName) {
        logger.info("Loading XML from file: " + fileName);
        String payload = FileUtils.readXmlFile(fileName);
        apiClient.setContentType(ContentType.XML);
        apiClient.setBody(payload);
        testContext.setContextData("lastPayload", payload);
    }

    @When("I load text payload from file {string}")
    public void loadTextPayloadFromFile(String fileName) {
        logger.info("Loading text from file: " + fileName);
        String payload = FileUtils.readTextFile(fileName);
        apiClient.setBody(payload);
        testContext.setContextData("lastPayload", payload);
    }

    @When("I build complex payload with following data:")
    public void buildComplexPayload(DataTable dataTable) {
        logger.info("Building complex payload");
        payloadBuilder.clear();

        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            String key = row.get("key");
            String value = row.get("value");
            String type = row.get("type");

            if ("string".equalsIgnoreCase(type)) {
                payloadBuilder.addString(key, value);
            } else if ("number".equalsIgnoreCase(type)) {
                payloadBuilder.addNumber(key, Integer.parseInt(value));
            } else if ("boolean".equalsIgnoreCase(type)) {
                payloadBuilder.addBoolean(key, Boolean.parseBoolean(value));
            }
        }
    }

    @When("I add nested object {string} to payload with:")
    public void addNestedObjectToPayload(String objectName, DataTable dataTable) {
        logger.info("Adding nested object: " + objectName);
        Map<String, Object> nestedData = new HashMap<>(dataTable.asMap());
        payloadBuilder.addNestedObject(objectName, nestedData);
    }

    @When("I add array {string} to payload with values:")
    public void addArrayToPayload(String arrayName, DataTable dataTable) {
        logger.info("Adding array: " + arrayName);
        List<String> values = dataTable.asList();
        payloadBuilder.addStringArray(arrayName, values);
    }

    @When("I add object array {string} to payload with:")
    public void addObjectArrayToPayload(String arrayName, DataTable dataTable) {
        logger.info("Adding object array: " + arrayName);
        List<Map<String, Object>> objectArray = new ArrayList<>();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            objectArray.add(new HashMap<>(row));
        }
        payloadBuilder.addObjectArray(arrayName, objectArray);
    }

    @When("I finalize payload and set to request")
    public void finalizePayload() {
        logger.info("Finalizing payload");
        String payload = payloadBuilder.build();
        apiClient.setBody(payload);
        testContext.setContextData("lastPayload", payload);
        payloadProcessor.logPayload(payload);
    }

    @When("I merge current payload with file {string}")
    public void mergePayloadWithFile(String fileName) {
        logger.info("Merging payload with file: " + fileName);
        String currentPayload = (String) testContext.getContextData("lastPayload");
        String filePayload = FileUtils.readJsonFile(fileName);
        String mergedPayload = payloadProcessor.mergePayloads(currentPayload, filePayload);

        apiClient.setBody(mergedPayload);
        testContext.setContextData("lastPayload", mergedPayload);
        payloadProcessor.logPayload(mergedPayload);
    }

    @When("I replace payload variables:")
    public void replacePayloadVariables(DataTable dataTable) {
        logger.info("Replacing payload variables");
        String currentPayload = (String) testContext.getContextData("lastPayload");
        Map<String, String> variables = dataTable.asMap();

        String processedPayload = payloadProcessor.processPayloadWithVariables(currentPayload, variables);
        apiClient.setBody(processedPayload);
        testContext.setContextData("lastPayload", processedPayload);
        payloadProcessor.logPayload(processedPayload);
    }

    @When("I validate payload contains required fields:")
    public void validatePayloadStructure(DataTable dataTable) {
        logger.info("Validating payload structure");
        String currentPayload = (String) testContext.getContextData("lastPayload");
        List<String> requiredFields = dataTable.asList();
        payloadProcessor.validatePayloadStructure(currentPayload, requiredFields);
    }

    @When("I extract field {string} from payload as {string}")
    public void extractFieldFromPayload(String fieldPath, String contextKey) {
        logger.info("Extracting field: " + fieldPath);
        String currentPayload = (String) testContext.getContextData("lastPayload");
        String extractedValue = payloadProcessor.extractFieldFromPayload(currentPayload, fieldPath);
        testContext.setExtractedData(contextKey, extractedValue);
        logger.info("Extracted: " + contextKey + " = " + extractedValue);
    }

    @When("I log current payload for debugging")
    public void logPayloadForDebugging() {
        String payload = (String) testContext.getContextData("lastPayload");
        logger.info("========== PAYLOAD DEBUG ==========");
        payloadProcessor.logPayload(payload);
        logger.info("===================================");
    }

    @When("I generate and add user data to payload")
    public void generateUserData() {
        logger.info("Generating user data");
        payloadBuilder.clear();
        payloadBuilder.addString("name", DataGenerator.generateName())
                .addString("email", DataGenerator.generateEmail())
                .addString("phone", DataGenerator.generatePhoneNumber())
                .addNumber("id", DataGenerator.generateRandomNumber(1, 1000))
                .addString("uuid", DataGenerator.generateUUID());
    }
}
