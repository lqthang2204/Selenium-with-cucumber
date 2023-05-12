@mc-feature-register
Feature: Login function

  @register-user
  Scenario: test register user
    Given I navigate to "https://www.sportchek.ca/"
    And I am user1 created by the file
    And I change the page spec to RegisterUser
    And I wait for element login-button to be ENABLED
    And I click element login-button
    And I change the page spec to SignInPage
    And I wait for element user-input to be DISPLAYED
    And I type "USER.1.email" into element user-input
    And I type "USER.1.password" into element password-input
    And I wait 1000 seconds


#    And I change the page spec to Page_DragDrop
#    And I drag and drop element amount_5000 to element amount_target_1 by javascript