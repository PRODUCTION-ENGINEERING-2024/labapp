Feature: Register User to Post
  Scenario: Successfully register a user to a post
    Given a post exists with ID "1" and details "Fotbal Crangasi", "Parc Crangasi", "2024-04-24 14:30", 18
    And a user exists with ID "1" and details "John", "Doe", 30, "johndoe"
    When I register user with ID "1" to post with ID "1"
    Then the registration should be successful
    
