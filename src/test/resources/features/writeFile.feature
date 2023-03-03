@regression
Feature: Login function

  @mc-WriteFileCSV
  Scenario: Negative to web URL
      And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I type "tin tuc" into element field-search
    And I click keyboard ENTER button on element field-search
    And I change the page spec to result
    And I wait for element result-search to be DISPALYED
    And I save text for element result-search with key "data_temp"
    And I generate test_file.csv file with header "column1, column2, column3"
    And I write "KEY.data_temp" into file test_file.csv

  @mc-WriteFileExcel
  Scenario: Negative to web URL
    And I navigate to "https://www.google.com.vn/"
    And I change the page spec to test
    And I type "tin tuc" into element field-search
    And I click keyboard ENTER button on element field-search
    And I change the page spec to result
    And I wait for element result-search to be DISPALYED
    And I save text for element result-search with key "data_temp"
    And I save text for element result-search-2 with key "data_temp-2"
    And I save text for element result-search-3 with key "data_temp-3"
    And I generate test_file.xlsx file with header "column1, column2, column3"
    And I write "KEY.data_temp" in the column 1 and row 2 with file test_file.xlsx
    And I write "KEY.data_temp-2" in the column 2 and row 2 with file test_file.xlsx
    And I write "KEY.data_temp-3" in the column 3 and row 2 with file test_file.xlsx