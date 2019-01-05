Feature: Create offers

  Background:
    As an API user, I require the ability to create new offers
    So that they will appear to customers

  Scenario: A valid offer is created
    When A user supplies a valid body to the offers/create endpoint
    Then The offer is successfully created

  Scenario: An invalid offer cannot be created - no id
    When A user supplies a request with no id to the offers/create endpoint
    Then The request is rejected

  Scenario: An invalid offer cannot be created - bad date
    When A user supplies a request with a malformed date to the offers/create endpoint
    Then The request is rejected

  Scenario: An invalid offer cannot be created - type mismatch
    When A user supplies a request with a mismatched type to the offers/create endpoint
    Then The request is rejected

  Scenario: An invalid offer cannot be created - bad json
    When A user supplies a request with malformed json to the offers/create endpoint
    Then The request is rejected

  Scenario: An invalid offer cannot be created - empty request
    When A user supplies an empty request to the offers/create endpoint
    Then The request is rejected