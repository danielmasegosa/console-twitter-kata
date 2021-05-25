Feature: Executing view the wall command

  Scenario: Received a command to view the wall the use case is fired
    Given a command to view the wall of a user
    When the view wall use case is fired with the received data
    Then the post from this user wall are retrieved