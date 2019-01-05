Feature: Search for offers

  Background:
    As an API user, I require the ability to search for offers
    So that I can retrieve deals which are currently valid

  Scenario: A search is performed with valid offers - no parameters are supplied
    When A user navigates to the offers/search endpoint without parameters
    Then All valid offers are returned

  Scenario: A search is performed with valid offers - valid parameters are supplied
    When A user navigates to the offers/search endpoint with valid parameters
    Then All valid offers are returned

  Scenario: A search is performed with valid offers - malformed parameters are supplied
    When A user navigates to the offers/search endpoint with malformed parameters
    Then The search returns an error