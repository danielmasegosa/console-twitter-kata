Feature: Executing post message command

  Scenario: Received a command to post a message and the use case is fired
    Given a command to post a message
    When the use case is fired with the received data
    Then the post is stored in the database