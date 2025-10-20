Feature: API CRUD Operations Testing
  As a QA Automation Engineer
  I want to test all CRUD operations on REST API
  So that I can ensure API functionality works correctly

  Background:
    Given Base URL is set to "https://jsonplaceholder.typicode.com"
    And Clear all context data

  @smoke @get
  Scenario: Get User by ID - Positive Test
    When I send GET request to "/users/1"
    Then Response status code should be 200
    And Response content type should be "application/json"
    And Response body should not be empty
    And JSON path "id" should equal to 1
    And JSON path "name" should contain "Leanne Graham"
    And Response time should be less than 5000 milliseconds

  @smoke @post
  Scenario: Create User - POST Request
    When I prepare request body with following data:
      | key   | value            |
      | name  | John Doe         |
      | email | john@example.com |
      | phone | 1234567890       |
    And I send POST request to "/users"
    Then Response status code should be 201
    And I extract JSON path "id" as "createdUserId"

  @regression @put
  Scenario: Update User - PUT Request
    Given I prepare request body with following data:
      | key   | value            |
      | id    | 11                |
      | name  | hello     |
      | email | hello@test.com |
    When I send PUT request to "/users/1"
    Then Response status code should be 200
    And JSON path "name" should equal to "hello"

  @regression @delete
  Scenario: Delete User - DELETE Request
    When I send DELETE request to "/users/1"
    Then Response status code should be 200

  @smoke @query
  Scenario: Query Parameters Testing
    When I send GET request to "/users" with query parameters:
      | id | 1 |
    Then Response status code should be 200
    And Response body should not be empty

  @smoke @headers
  Scenario: Headers Validation
    When I add header "X-Custom-Header" with value "TestValue"
    And I send GET request to "/users/1"
    Then Response status code should be 200
    And Response header "Content-Type" should contain "application/json; charset=utf-8"

  @regression @array
  Scenario: Array Response Validation
    When I send GET request to "/users"
    Then Response status code should be 200
    And JSON path "id" should have array size of 10

  @smoke @error
  Scenario: Negative Testing - Not Found
    When I send GET request to "/users/99999"
    Then Response status code should be 404

  @error @4xx
  Scenario: Error Response Validation
    When I send GET request to "/invalid/endpoint"
    Then Response status code should be 404
    And Response status code should be 4xx
