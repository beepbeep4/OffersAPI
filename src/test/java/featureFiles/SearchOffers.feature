Feature: Search for offers

  Background:
    As an API user, I require the ability to search for offers
    So that I can retrieve deals which are currently valid

  Scenario: A search is performed with no parameters
    When A user navigates to the offers/search endpoint without parameters
    Then All valid offers are returned

  Scenario: A search is performed with valid parameters
    When A user navigates to the offers/search endpoint with valid parameters
    Then All valid offers are returned

  Scenario: A search is performed with malformed parameters
    When A user navigates to the offers/search endpoint with malformed parameters
    Then The search returns an error

  Scenario: A search is performed with an expired offer id
    When A user navigates to the offers/search endpoint with an expired id
    Then The user is warned that the offer has expired

  Scenario: A search is performed with a cancelled offer id
    When A user navigates to the offers/search endpoint with a cancelled id
    Then The user is warned that the offer is cancelled