Feature: Login function
  Scenario: Negative to web URL
    Given I Navigate with URl is "https://www.google.com.vn/"
#    Given I Navigate with URl is "https://zingnews.vn/"
    And I change the page spec to test
    And I type "lqthang" into element field-search
    And I click element button-search
    And I Navigate with URl is "http://live.techpanda.org/index.php/"
    And I change the page spec to HomePage
    And I wait for element page-title to be DISPLAYED
    And I perform to action buy-one-mobile
#    And I verify the text for element page-title is "This is demo site for"
#    And I wait for element mobile-button to be DISPLAYED
#    And I click element mobile-button
#    And I change the page spec to MobilePage
#    And I wait for element product-one to be DISPLAYED
#    And I click element product-one
#    And I clear text from element field-search
#    And I scroll to element bottom-page