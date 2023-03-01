@feature-mc-21944 @pmtsearch @smoke @aou @regression
Feature: PMT Search
  As a PMT user
  I should be able to do search on PMT
  I should be able to search all existing participants regardless of organization
  I should be able to search only prospects that pair to same organization

  @mcthang
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