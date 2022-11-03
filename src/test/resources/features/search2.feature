
Feature: Search function
  @mc-1
  Scenario: test Search function
    Given I navigate to "http://live.techpanda.org/index.php/"
#    And I navigate to "https://www.google.com/"
#    And I close browser with title is "test"
    And I change the page spec to HomePage
    And I wait for element mobile-button to be DISPLAYED
    And I click element mobile-button
#    And I wait for element link-text to be DISPLAYED
#    And I save the text for element link-text is "link"
    And I wait for element product-one to be DISPLAYED
    And I save the text for element product-one with key "product"
    And I become a random user
    And I perform to action buy-one-mobile with override values
#      | search-input | KEY.product |
      | search-input | USER.email |
#    And I click keyboard HOVER button on element dropdown-search
#    And I click element dropdown-search
#    And I click keyboard HOVER-AND-CLICK button on element dropdown-search
#    And I click keyboard RIGHT-CLICK button on element search-input
#    And I click keyboard DOUBLE-CLICK button on element dropdown-search
#    And I click keyboard ENTER button on element search-input
#    And I click keyboard ENTER button on element search-input
#    And I close browser with title is "Mobile"
#    And I close browser with title


  @mc-2
  Scenario: test Search function
    Given I navigate to "http://live.techpanda.org/index.php/"
#    And I Navigate with URl is "https://www.google.com/"
#    And I close browser with title is "test"
    And I change the page spec to HomePage
    And I wait for element mobile-button to be DISPLAYED
    And I click element mobile-button
#    And I wait for element link-text to be DISPLAYED
#    And I save the text for element link-text is "link"
    And I wait for element product-one to be DISPLAYED
    And I save the text for element product-one with key "product"
    And I perform to action buy-one-mobile with override values
      | search-input | KEY.product |
    And I click keyboard HOVER button on element dropdown-search
    Then I click element dropdown-search  
    And I navigate to refresh-page
#    And I navigate to "refresh-page"
#    And I click element dropdown-search
#    And I click keyboard HOVER-AND-CLICK button on element dropdown-search
#    And I click keyboard RIGHT-CLICK button on element search-input
#    And I click keyboard DOUBLE-CLICK button on element dropdown-search
#    And I click keyboard ENTER button on element search-input
#    And I click keyboard ENTER button on element search-input
#    And I close browser with title is "Mobile"
#    And I close browser with title
#  mvn install -Dcucumber.options="--tags @runThis --tags ~@ignore --format json-pretty:target/cucumber-report-myReport.json --format html:target/cucumber-html-report-myReport"
