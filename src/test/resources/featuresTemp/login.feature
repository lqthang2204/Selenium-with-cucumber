@feature-mc-21946 @regression
Feature: Login function

  @mc-login
  Scenario: Negative to web URL
#    Given I navigate to "https://zingnews.vn/"
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I become a random user
    And I type "USER.email" into element field-search
    And I clear text from element field-search
    And I type "lqthang" into element field-search
    And I save text for element field-search with key "name"
    And I click element button-search
    And I navigate to "https://demo.openmrs.org/openmrs/login.htm"
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
#    And I verify the text for element error-message is "Usuario/contraseña inválida. Por favor inténtelo nuevamente."
    And I navigate to refresh-page
    And I type "Admin" into element user-field
    And I type "Admin123" into element pass-field
    And I save text for element location-option-inpatient with key "location"
    And I click element location-option-inpatient
    And I click element login-button
    And I change the page spec to IndexPage
    And I wait for element welcome-user to be DISPLAYED
    And I verify the text for element welcome-user is "KEY.location"
    And I save text for element welcome-user with key "user"
    And I wait for element log-out to be DISPLAYED
    And I click element log-out
    And I change the page spec to LoginPage
    And I wait for element user-field to be DISPLAYED
    And I run test.json with data.json data file with override values
      | test2 | KEY.name |
    And I run test.json with data.json data file
    And I run test.json with data.json data file with override values
      | search-input | USER.email |

  @mc-test123456
  Scenario: Negative to web URL
    Given I navigate to "https://zingnews.vn/"
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I type "Đăng nhập" into element field-search
    And I save text for element field-search with key "name"
    And I clear text from element field-search
    And I click element ui-text-equal with text "Đăng nhập"
    And I wait for element ui-text-equal with text "KEY.name" to be DISPLAYED
    And I click element ui-text-equal with text "KEY.name"
    And I wait for element ui-text-equal with text "Tiếp theo" to be DISPLAYED
#      And I become a random user
#    And I run test.json with data.json data file
#    And I run test.json with data.json data file with override values
#      | test2 | KEY.name |
#    And I run postman collection with link "https://www.getpostman.com/collections/be1065f3f80e40af800f"






