@regression
Feature: Login function

  @mc-WriteFile
  Scenario: Negative to web URL
#    And I navigate to "https://www.google.com.vn/"
#    And I change the page spec to test
#    And I become a random user
#    And I type "tin tuc" into element field-search
#    And I click element button-search
#    And I change the page spec to result
#    And I wait for element result-search to be DISPALYED
#    And I generate test_file.csv file with header "column1, column2, column3"
    And I write "data" into file "test_file.csv"