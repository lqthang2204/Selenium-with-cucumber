@mc-feature-register
Feature: Login function

  @register-user
  Scenario: test register user
    And I become a random user
    Given I navigate to "https://www.sportchek.ca/"
    And I am user1 created by the file
#    And I change the page spec to Page_DragDrop
#    And I drag and drop element amount_5000 to element amount_target_1 by javascript