Feature: Login function
  Scenario: Negative to web URL
    Given I Navigate with URl is "https://www.google.com.vn/"
#    Given I Navigate with URl is "https://zingnews.vn/"
    And I change the page spec to test
    And I type "lqthang" into element field-search
    And I click element button-search
    And I Navigate with URl is "https://demo.openmrs.org/openmrs/login.htm"
    And I change the page spec to LoginPage
    And I wait for element user-field to be DISPLAYED
    And I wait for element pass-field to be DISPLAYED
    And I type "Admidsdsdsn" into element user-field
    And I type "Admin12dsdsds3" into element pass-field
    And I click element location-option-inpatient
    And I wait for element login-button to be ENABLED
    And I click element login-button
    And I wait for element error-message to be DISPLAYED
    And I verify the text for element error-message is "Invalid username/password. Please try again."
    And I type "Admin" into element user-field
    And I type "Admin123" into element pass-field
    And I save the text for element location-option-inpatient is "location"
    And I click element location-option-inpatient
    And I click element login-button
    And I change the page spec to IndexPage
    And I wait for element welcome-user to be DISPLAYED
    And I verify the text for element welcome-user is "KEY.location"
    And I save the text for element welcome-user is "user"
    And I wait for element log-out to be DISPLAYED
    And I click element log-out
    And I change the page spec to LoginPage
    And I wait for element user-field to be DISPLAYED
    d

