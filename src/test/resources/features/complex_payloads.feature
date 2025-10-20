Feature: Complex Payload Testing
  As a QA Automation Engineer
  I want to test complex payload scenarios
  So that I can ensure complex data handling works correctly

  Background:
    Given Base URL is set to "https://jsonplaceholder.typicode.com"
    And Clear all context data

  @payload @file
  Scenario: Send JSON payload from file
    When I load JSON payload from file "simple_user.json"
    And I send POST request to "/users"
    Then Response status code should be 201
    And Response body should not be empty

  @payload @file @variables
  Scenario: Send JSON with variable replacement
    When I load JSON payload from file "complex_user.json" with replacements:
      | USER_NAME    | John Smith      |
      | USER_EMAIL   | john@domain.com |
      | PHONE_NUMBER | 5551234567      |
    And I send POST request to "/users"
    Then Response status code should be 201

  @payload @build
  Scenario: Build complex payload dynamically
    When I build complex payload with following data:
      | key      | value     | type    |
      | name     | Jane Doe  | string  |
      | email    | jane@test | string  |
      | age      | 30        | number  |
      | verified | true      | boolean |
    And I add nested object "address" to payload with:
      | street | 123 Main St |
      | city   | New York    |
      | state  | NY          |
    And I add array "tags" to payload with values:
      | developer |
      | tester    |
      | manager   |
    And I finalize payload and set to request
    And I send POST request to "/users"
    Then Response status code should be 201

  @payload @merge
  Scenario: Merge multiple payloads
    When I load JSON payload from file "simple_user.json"
    And I merge current payload with file "simple_user.json"
    And I send POST request to "/users"
    Then Response status code should be 201

  @payload @validate
  Scenario: Validate payload structure before sending
    When I load JSON payload from file "complex_user.json" with replacements:
      | USER_NAME    | Test User  |
      | USER_EMAIL   | test@test  |
      | PHONE_NUMBER | 5551234567 |
    And I validate payload contains required fields:
      | name  |
      | email |
    And I send POST request to "/users"
    Then Response status code should be 201

  @payload @extract
  Scenario: Extract nested field from payload
    When I load JSON payload from file "complex_user.json" with replacements:
      | USER_NAME    | John Doe   |
      | USER_EMAIL   | john@test  |
      | PHONE_NUMBER | 5551234567 |
    And I send POST request to "/users"
    Then Response status code should be 201
    And I extract JSON path "id" as "userId"

