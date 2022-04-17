

@mcthang
Feature: Search function
  Scenario Outline: test Search function
    Given I Navigate with URl is "http://live.techpanda.org/index.php/"
    And I change the page spec to HomePage
    And I wait for element mobile-button to be DISPLAYED
    And I click element <product>
    And I type "test " into element search-input
    And I perform to action buy-one-mobile with override values
      | search-input | test mobile |

    Examples:
      | product                  |
      | mobile-button      |
      | tv-button  |