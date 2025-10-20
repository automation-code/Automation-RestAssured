Feature: API Authentication Testing
  As a QA Automation Engineer
  I want to test different authentication methods
  So that I can ensure secure API access

  Background:
    Given Base URL is set to "https://jsonplaceholder.typicode.com"
    And Clear all context data

  @auth @bearer
  Scenario: Send request with Bearer Token
    When I add bearer token "test-token-12345"
    And I send GET request to "/users/1"
    Then Response status code should be 200

  @auth @header
  Scenario: Send request with custom header
    When I add header "Authorization" with value "Basic dGVzdDp0ZXN0"
    And I send GET request to "/users/1"
    Then Response status code should be 200
