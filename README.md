# Automation RestAssured


## Complex Payload Usage
### Example 1: Load Simple JSON from File
```java
@When("I load user payload from file")
public void loadUserPayload() {
    String payload = FileUtils.readJsonFile("simple_user.json");
    apiClient.setBody(payload);
}
```
**Test Data File: simple_user.json**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "active": true
}
```

### Example 2: Load with Variable Replacement
```java
@When("I load complex user with replacements:")
public void loadComplexUser(DataTable dataTable) {
    Map replacements = dataTable.asMap();
    String payload = FileUtils.readJsonFileWithReplacements(
        "complex_user.json",
        "USER_NAME", replacements.get("name"),
        "USER_EMAIL", replacements.get("email"),
        "PHONE_NUMBER", replacements.get("phone")
    );
    apiClient.setBody(payload);
}
```

**Feature File Usage:**
```gherkin
Scenario: Create user with variable replacement
  When I load complex user with replacements:
    | name  | John Smith      |
    | email | john@domain.com |
    | phone | 5551234567      |
  And I send POST request to "/users"
  Then Response status code should be 201
```

### Example 3: Build Complex Payload Dynamically
```java
@When("I build order payload with nested data:")
public void buildOrderPayload(DataTable dataTable) {
    ComplexPayloadBuilder builder = new ComplexPayloadBuilder();
    
    builder.addString("orderId", "ORD-" + System.currentTimeMillis())
           .addNumber("customerId", 12345)
           .addBoolean("rushDelivery", true);
    
//    Add nested address object
    Map address = new HashMap<>();
    address.put("street", "123 Main St");
    address.put("city", "New York");
    address.put("state", "NY");
    builder.addNestedObject("address", address);
    
//    Add items array
    List items = Arrays.asList("ITEM001", "ITEM002", "ITEM003");
    builder.addStringArray("items", items);
    
    String payload = builder.build();
    apiClient.setBody(payload);
    PayloadProcessor processor = new PayloadProcessor();
    processor.logPayload(payload);
}
```

### Example 4: Merge Multiple Payloads
```java
@When("I merge base payload with order items:")
public void mergePayloads() {
    String basePayload = FileUtils.readJsonFile("order_base.json");
    String itemsPayload = FileUtils.readJsonFile("order_items.json");
    
    PayloadProcessor processor = new PayloadProcessor();
    String mergedPayload = processor.mergePayloads(basePayload, itemsPayload);
    
    apiClient.setBody(mergedPayload);
}
```

### Example 5: Extract and Use Data from Response
```java
@When("I extract order ID and use it for next request")
public void extractAndUseData() {
    Response response = apiClient.getResponse();
    String orderId = new ResponseValidator(response)
        .extractJsonPath("data.orderId")
        .toString();
    
    testContext.setExtractedData("orderId", orderId);
    
//    Use extracted data for next request
    String nextPayload = FileUtils.readJsonFileWithReplacements(
        "order_update.json",
        "ORDER_ID", orderId
    );
    apiClient.setBody(nextPayload);
}
```

### Example 6: Dynamic Test Data Generation
```java
@When("I create user with generated data")
public void createUserWithGeneratedData() {
    ComplexPayloadBuilder builder = new ComplexPayloadBuilder();
    
    builder.addString("name", DataGenerator.generateName())
           .addString("email", DataGenerator.generateEmail())
           .addString("phone", DataGenerator.generatePhoneNumber())
           .addNumber("age", DataGenerator.generateRandomNumber(18, 65))
           .addString("id", DataGenerator.generateUUID());
    
    String payload = builder.build();
    apiClient.setBody(payload);
}
```

### 7. Performance Testing
```java
@When("I measure request performance")
public void measurePerformance() {
    PerformanceMetrics metrics = new PerformanceMetrics();
    Response response = apiClient.get("/users/1");
    metrics.recordEndpointPerformance("/users/1", response);
    metrics.printPerformanceSummary();
}
```

## Logging Configuration

### Log4j.properties Features
```properties
# Level 1: Console Output (Real-time debugging)
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%-5p] [%t] %c{1}:%M - %m%n

# Level 2: File Output (General test execution)
log4j.appender.FILE=org.apache.log4j.RollingFileAppender
log4j.appender.FILE.File=logs/test-execution.log
log4j.appender.FILE.MaxFileSize=10MB
log4j.appender.FILE.MaxBackupIndex=10

# Level 3: Error File (Error tracking)
log4j.appender.ERROR_FILE=org.apache.log4j.RollingFileAppender
log4j.appender.ERROR_FILE.File=logs/test-execution-error.log
log4j.appender.ERROR_FILE.Threshold=ERROR
```

### Logging Best Practices in Code
```java
private static Logger logger = Logger.getLogger(ClassName.class);

//Before request
logger.info("Sending GET request to: " + endpoint);
//After request
logger.info("Response Status Code: " + response.statusCode());
//For debugging
logger.debug("Response Body: " + response.body().asString());
//For errors
logger.error("Error occurred: " + exception.getMessage(), exception);
```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Tests with Specific Tags
```bash
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@smoke and @regression"
mvn test -Dcucumber.filter.tags="not @skip"
```

### Run Specific Feature File
```bash
mvn test -Dcucumber.features="src/test/resources/features/complex_payloads.feature"
```

### Generate HTML Report
```bash
mvn test
# Report generated at: target/cucumber-reports/index.html
```

## Sample Feature File

```gherkin
@api @smoke
Feature: Complete API Testing with Complex Payloads

  Background:
    Given Base URL is set to "https://api.example.com"
    And Clear all context data

  @payload @file
  Scenario: Load and send JSON from file
    When I load JSON payload from file "order_payload.json"
    And I send POST request to "/orders"
    Then Response status code should be 201
    And Response body should not be empty
    And I extract JSON path "id" as "orderId"

  @payload @dynamic
  Scenario: Build and send complex payload
    When I build complex payload with following data:
      | key      | value       | type    |
      | name     | Test User   | string  |
      | email    | test@test   | string  |
      | verified | true        | boolean |
    And I add nested object "address" to payload with:
      | street | 123 Main St |
      | city   | New York    |
    And I finalize payload and set to request
    And I send POST request to "/users"
    Then Response status code should be 201

  @payload @merge
  Scenario: Merge multiple payloads
    When I load JSON payload from file "base_order.json"
    And I merge current payload with file "order_items.json"
    And I send POST request to "/orders"
    Then Response status code should be 201

  @payload @validation
  Scenario: Validate payload structure before sending
    When I load JSON payload from file "complex_user.json" with replacements:
      | USER_NAME    | John Doe    |
      | USER_EMAIL   | john@test   |
      | PHONE_NUMBER | 5551234567  |
    And I validate payload contains required fields:
      | name    |
      | email   |
      | address |
    And I send POST request to "/users"
    Then Response status code should be 201
```