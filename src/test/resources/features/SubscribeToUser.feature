Feature: Executing subscribe to a user command

  Scenario: Received a command to subscribe to a user the use case is fired
    Given a command to to subscribe to a user
    When create subscription use case is fired with the received data
    Then the list of the followee from this user are retrieved