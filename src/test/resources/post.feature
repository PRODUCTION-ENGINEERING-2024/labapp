Feature: Register User to Post
  In order to facilitate user participation in posts
  As a system
  I want to register users to posts

  Scenario: Successfully register a user to a post
    Given a post exists with ID "1" and details "Fotbal Crangasi", "Parc Crangasi", "2024-04-24 14:30", 18
    And a user exists with ID "1" and details "John", "Doe", 30, "johndoe"
    When I register user with ID "1" to post with ID "1"
    Then the registration should be successful
    And the response should be "User registered to post"
