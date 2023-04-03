@mc-drag-drop
Feature: Login function

  @getElement
  Scenario: Negative to web URL
    Given I navigate to "https://demo.guru99.com/test/drag_drop.html"
#    Given I navigate to "https://jqueryui.com/droppable/"
    And I change the page spec to Page_DragDrop
    And I drag and drop element amount_5000 to element amount_target_1 by javascript