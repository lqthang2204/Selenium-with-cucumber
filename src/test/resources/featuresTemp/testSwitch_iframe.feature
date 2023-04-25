@frame
Feature: Search function
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

#    And I navigate to "https://www.google.com/"
#    And I close browser with title is "test"
