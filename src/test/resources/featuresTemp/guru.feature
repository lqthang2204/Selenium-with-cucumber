@Guru
Feature: Login function

  @mc-guru1
  Scenario: Negative to web URL
    Given I navigate to "https://www.google.com.vn/"
#    Given I Navigate with URl is "https://zingnews.vn/"
    And I change the page spec to test
    And I type "lqthang" into element field-search
    And I click element button-search
    And I navigate to "http://live.techpanda.org/index.php/"
    And I change the page spec to HomePage
    And I wait for element page-title to be EXIST
    And I perform buy-one-mobile action
#    And I verify the text for element page-title is "Mobile"
    And I wait for element mobile-button to be ENABLED
    And I click element mobile-button
    And I change the page spec to MobilePage
    And I wait for element product-one to be ENABLED
    And I click element product-one
    And I scroll to element bottom-page


  @mc-guru2
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

#  @mc-guru3
#  Scenario: Negative to web URL
#    And I navigate to "https://www.google.com.vn/"
#    And I change the page spec to test
#    And I type "Đăng nhập" into element field-search
#    And I save text for element field-search with key "name"
#    And I clear text from element field-search
#    And I click element ui-text-equal with text "Đăng nhập"
#    And I wait for element ui-text-equal with text "KEY.name" to be DISPLAYED
#    And I click element ui-text-equal with text "KEY.name"
#    And I wait for element ui-text-equal with text "Tiếp theo" to be DISPLAYED
#
#  @mc-guru4
#  Scenario: Negative to web URL
#    Given I navigate to "https://demo.guru99.com/test/drag_drop.html"
##    Given I navigate to "https://jqueryui.com/droppable/"
#    And I change the page spec to Page_DragDrop
#    And I drag and drop element amount_5000 to element amount_target_1
#
#  @mc-guru5
#  Scenario: Negative to web URL
#    And I navigate to "https://www.google.com.vn/"
#    And I change the page spec to test
#    And I type "tin tuc" into element field-search
#    And I click keyboard ENTER button on element field-search
#    And I change the page spec to result
#    And I wait for element result-search to be DISPALYED
#    And I save text for element result-search with key "data_temp"
#    And I save text for element result-search-2 with key "data_temp-2"
#    And I save text for element result-search-3 with key "data_temp-3"
#    And I generate test_file.csv file with header "column1, column2, column3"
#    And I write "KEY.data_temp,KEY.data_temp-2,KEY.data_temp-3" into file test_file.csv
#    And I change the page spec to test
#    And I type "zing" into element field-search
#    And I click keyboard ENTER button on element field-search
#    And I change the page spec to result
#    And I wait for element result-search to be DISPALYED
#    And I save text for element result-search with key "data_temp_4"
#    And I write "KEY.data_temp_4,," into file test_file.csv
#    And I change the page spec to test
#    And I type "column1.1" from test_file.csv into element field-search

  @mc-guru6
  Scenario Outline: test Search function
    Given I navigate to "http://live.techpanda.org/index.php/"
    And I change the page spec to HomePage
    And I wait for element mobile-button to be DISPLAYED
    And I click element <product>
    And I type "test " into element search-input
    And I perform buy-one-mobile action with override values
      | search-input | test mobile |
    Examples:
      | product        |
      | mobile-button  |
      | tv-button  |

  @test_function_switch_frame
  Scenario: test switch iFrame
    Given I navigate to "https://demo.guru99.com/test/guru99home/"
    And I change the page spec to indexGuru
    And I switch to iFrame for element iframe-jmeter
    And I wait for element image-in-iframe to be DISPLAYED
    And I click element image-in-iframe
    And I switch to default iFrame
    And I wait for element selenium-icon to be ENABLED
    And I click element selenium-icon
    And I wait 10 seconds
    And I switch to browser window with index "2"
    And I change the page spec to seleniumPage
    And I wait for element title-page to be DISPLAYED
    And I verify the text for element title-page is "Selenium Live Project: FREE Real Time Project for Practice"

  @mc-guru7
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
