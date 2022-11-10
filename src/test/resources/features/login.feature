Feature: Login function
  @mc-login
  Scenario: Negative to web URL
#    Given I navigate to "https://zingnews.vn/"
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I become a random user
    And I type "USER.email" into element field-search
    And I type "lqthang" into element field-search
#    And I click element button-search
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
    Given I navigate to "https://zingnews.vn/"
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I type "Đăng nhập" into element field-search
    And I save the text for element field-search with key "name"
    And I clear text from element field-search
    And I click element ui-text-equal with text "Sign in"
    And I wait for element ui-text-equal with text "KEY.name" to be DISPLAYED
    And I click element ui-text-equal with text "KEY.name"
    And I wait for element ui-text-equal with text "Next" to be DISPLAYED
      And I become a random user
    And I run test.json with data.json data file
    And I run test.json with data.json data file with override values
      | test2 | KEY.name |
    And I run postman collection with link "https://www.getpostman.com/collections/be1065f3f80e40af800f"

  @mc-loginPMT
  Scenario: Logib PMT and changeRole
    Given I navigate to "https://pmt-thangle.qak8s.vibrenthealth.com/"
    And I change the page spec to AdminLoginPage
    And I perform fill-admin-sign-in-form action with override values
      |username-input | mcadmin@vibrenthealth.com |
      |password-input | Password123$               |
#    And I type "mcadmin@vibrenthealth.com" into element username-input
#    And I type "Password123$" into element password-input
#    And I click element submit-button
    And I change the page spec to HomePagePMT
    And I perform change-to-system-admin-role action
    And I wait for element user-admin-icon to be DISPLAYED
    And I click element user-admin-icon
    And I change the page spec to UserAdminPage
    And I wait for element spinner to be NOT_DISPLAYED
    And I change the page spec to HomePagePMT
    And I perform wait-for-10-seconds action
    And I change the page spec to UserAdminPage
    And I wait for element add-user to be ENABLED
    And I click element add-user
    And I change the page spec to AddEditUserPage
    And I become a random user
    And I perform create-program-manager-user action with override values
      |first-name-input | USER.firstName|
      |last-name-input  |USER.lastName  |
      |email-input      |USER.email     |
    And I change the page spec to UserAdminPage
    And I wait for element success-alert to be DISPLAYED

#  https://pmt-pmi-qa.qak8s.vibrenthealth.com/
  @mc-Cati
  Scenario: Run function Cati
    Given I navigate to "https://pmt-dho.qak8s.vibrenthealth.com/"
    And I change the page spec to AdminLoginPage
    And I perform fill-admin-sign-in-form action with override values
#          |username-input | quangthangle1402@gmail.com |
#      |password-input | Password123$              |
      |username-input | mcadmin@vibrenthealth.com |
      |password-input | Password123$              |
#  gaylene.bashirian@hotmail.com|
#    And I type "mcadmin@vibrenthealth.com" into element username-input
#    And I type "Password123$" into element password-input
#    And I click element submit-button
    And I change the page spec to HomePagePMT
    And I perform change-to-cati-interviewer-role action
    And I become a random user
    And I run CreateParticipant.postman_collection.json with ParticipantUserData.json data file
    And I click element search-icon
    And I change the page spec to SearchPage
    And I perform go-to-participant-profile-page action with override values
#    |email-address-input|USER.email|
      |email-address-input|USER.email|
    And I change the page spec to ProfilePage
    # END: CATI user able to search the participant and access profile
    # START: Start CATI session
    And I wait for element start-cati-button to be DISPLAYED
    And I click element start-cati-button
    And I click element verify-cati-consent-checkmark
    And I click element consent-button
    And I wait for element cati-password-input to be DISPLAYED
    And I type "Password123$" into element cati-password-input
    And I click element cati-confirm-password-btn
    And I wait for element verify-button to be DISPLAYED
    And I perform complete-step-verify-participant-identity action
    And I wait for element launch-cati-button to be ENABLED
    And I click element launch-cati-button
    And I switch to browser window with index "2"
    And I change the page spec to PMIHomePage
    And I wait for element zip-code-field to be DISPLAYED
    And I type "85351" into element zip-code-field
    And I click element ui-text-equal with text " Continue "
    And I wait for element start-button to be ENABLED
    And I click element start-button
    And I wait for element banner-welcome to be DISPLAYED
    And I verify the text for element banner-welcome is "USER.email"
    And I click element ui-text-equal with text " Log Out "
    And I wait for element email-input to be DISPLAYED
    And I switch to browser window with index "1"
    And I change the page spec to ProfilePage
    And I wait for element start-cati-button to be ENABLED





