Feature: Register User to Post
  Scenario: Successfully register a user to a post
    Given a post exists with Name "Fotbal Crangasi"
    Given a user exists with UserName "johndoe"
    When I register user with UserName "johndoe" to post with name "Fotbal Crangasi"
    Then the registration should be successful
    
