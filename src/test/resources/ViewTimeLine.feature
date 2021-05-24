Feature: Executing view timeline command

  Scenario: Received a command to view the timeline the use case is fired
    Given a command to view the timeline of a user
    When the view timeline use case is fired with the received data
    Then the post from this user are retrieved