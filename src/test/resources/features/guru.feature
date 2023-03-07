@mc-guru
Feature: Login function
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