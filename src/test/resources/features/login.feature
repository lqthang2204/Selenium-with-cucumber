Feature: Login function
  @mc-login
  Scenario: Negative to web URL
#    Given I navigate to "https://zingnews.vn/"
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I become a random user
    And I type "USER.email" into element field-search
    And I type "lqthang" into element field-search
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
    And I navigate to refresh-page
    And I type "Admin" into element user-field
    And I type "Admin123" into element pass-field
    And I save the text for element location-option-inpatient with key "location"
    And I click element location-option-inpatient
    And I click element login-button
    And I change the page spec to IndexPage
    And I wait for element welcome-user to be DISPLAYED
    And I verify the text for element welcome-user is "KEY.location"
    And I save the text for element welcome-user with key "user"
    And I wait for element log-out to be DISPLAYED
    And I click element log-out
    And I change the page spec to LoginPage
    And I wait for element user-field to be DISPLAYED
#    And I run test.json with data.json data file with override values
#      | test2 | KEY.name |
#    And I run test.json with data.json data file
#    And I run test.json with data.json data file with override values
#      | search-input | USER.email |

  @mc-test2
  Scenario: Negative to web URL
#    Given I navigate to "https://zingnews.vn/"
#    And I navigate to "https://www.google.com.vn/"
#    And I change the page spec to test
#    And I type "Đăng nhập" into element field-search
#    And I save the text for element field-search with key "name"
#    And I clear text from element field-search
#    And I click element ui-text-equal with text "Đăng nhập"
#    And I wait for element ui-text-equal with text "KEY.name" to be DISPLAYED
#    And I click element ui-text-equal with text "KEY.name"
#    And I wait for element ui-text-equal with text "Tiếp theo" to be DISPLAYED
#      And I become a random user
#    And I run test.json with data.json data file
#    And I run test.json with data.json data file with override values
#      | test2 | KEY.name |
    And I run postman collection with link "https://www.getpostman.com/collections/be1065f3f80e40af800f"

  @mc-loginPMT
  Scenario: Logib PMT and changeRole
    Given I navigate to "https://pmt-pmi-qa.qak8s.vibrenthealth.com/"
    And I change the page spec to AdminLoginPage
    And I type "mcadmin@vibrenthealth.com" into element username-input
    And I type "Password123$" into element password-input
    And I click element submit-button
    And I change the page spec to HomePagePMT
    And I perform to action change-to-system-admin-role
    And I wait for element user-admin-icon to be DISPLAYED
#    And I click element user-admin-icon
#    And I change the page spec to UserAdminPage
#    And I wait for element spinner to be NOT_DISPLAYED
#    And I click element add-user
#    And I



