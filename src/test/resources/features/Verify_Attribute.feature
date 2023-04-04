@feature-mc-21946 @regression
Feature: Login function

  @mc-attribute
  Scenario: Negative to web URL
#    Given I navigate to "https://zingnews.vn/"
    Given I navigate to "https://demo.openmrs.org/openmrs/login.htm"
    And I change the page spec to LoginPage
    And I verify attribute element user-field has css property placeholder with value "Enter your username"
    And I verify attribute element location-option-inpatient has css property value with value "6"
    And I become a random user
    And I type "USER.email" into element field-search
    And I clear text from element field-search
    And I type "lqthang" into element field-search
    And I save text for element field-search with key "name"
    And I click element button-search
    And I navigate to "https://demo.openmrs.org/openmrs/login.htm"
    And I change the page spec to LoginPage

