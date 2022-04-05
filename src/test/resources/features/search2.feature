Feature: Search function
  Scenario: test Search function
    Given I Navigate with URl is "http://live.techpanda.org/index.php/"
    And I change the page spec to HomePage
    And I wait for element mobile-button to be DISPLAYED
    And I perform to action buy-one-mobile with override values
      | search-input | test mobile |