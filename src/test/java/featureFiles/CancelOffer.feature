Feature: Cancel offers

  Background:
    As an API user, I require the ability to cancel existing offers
    So that they no longer appear to customers

  Scenario: An offer can be cancelled
    When A user supplies a valid id to the offers/cancel endpoint
    Then The offer is successfully cancelled

  Scenario: An offer cannot be cancelled without a valid offer id - invalid id
    When A user supplies an invalid id to the offers/cancel endpoint
    Then An error is returned

  Scenario: An offer cannot be cancelled without a valid offer id - no id
    When A user supplies no id to the offers/cancel endpoint
    Then An error is returned