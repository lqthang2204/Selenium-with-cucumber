@regression
Feature: Login function

  @mc-keyboard
  Scenario: Negative to web URL
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I become a random user
    And I type "USER.email" into element field-search
    And I click keyboard NUMPAD6 button on element field-search
    And I change the page spec to result
    And I wait for element result-search to be DISPALYED
    And I save text for element result-search with key "data_temp"
    And I generate test_file.csv file with header "column1, column2, column3"
    And I write "KEY.data_temp" into file "test_file.csv"