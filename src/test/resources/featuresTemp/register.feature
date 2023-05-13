@mc-feature-register
Feature: Login function

  @register-user
  Scenario: test register user
    Given I navigate to "https://www.sportchek.ca/"
    And I am user1 created by the file
    And I change the page spec to RegisterUser
    And I wait for element register-button to be ENABLED
    And I click element register-button
    And I wait for element user-input to be DISPLAYED
    And I type "USER.1.email" into element user-input
    And I type "USER.1.password" into element password-input
    And I type "USER.1.password" into element confirm-password-input
    And I wait for element create-user-button to be ENABLED
    And I click element create-user-button
    And I wait for element check-box-confirm to be DISPLAYED
    And I click element check-box-confirm
    And I click element create-user-button
    And I wait for element confirm-no-button to be ENABLED
    And I click element confirm-no-button
    And I wait 20 seconds
    And I navigate to the page to verify email "USER.1.email"
    And I switch to iFrame for element frame-sport-check
    And I wait for element verify-email-button to be DISPLAYED
    And I click element verify-email-button
    And I switch to browser window with index "3"
    And I change the page spec to ProductSport
    And I wait for element welcome-user to be DISPLAYED
    And I verify the text for element welcome-user is "USER.1.email"
    And I switch to browser window with index "1"



#    And I change the page spec to Page_DragDrop
#    And I drag and drop element amount_5000 to element amount_target_1 by javascript