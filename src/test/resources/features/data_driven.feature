Feature: Data-Driven Testing
  As a QA Automation Engineer
  I want to test with multiple data sets
  So that I can ensure robust API functionality

  Background:
    Given Base URL is set to "https://jsonplaceholder.typicode.com"

  @data-driven
  Scenario Outline: Create multiple users
    When I prepare request body with following data:
      | key   | value   |
      | name  | <name>  |
      | email | <email> |
    And I send POST request to "/users"
    Then Response status code should be 201

    Examples:
      | name       | email             |
      | User One   | user1@example.com |
      | User Two   | user2@example.com |
      | User Three | user3@example.com |

