@mc-drag-drop
Feature: Login function
  @mc-drag_and_drop
  Scenario: Negative to web URL
    Given I navigate to "https://demo.guru99.com/test/drag_drop.html"
#    Given I navigate to "https://jqueryui.com/droppable/"
    And I change the page spec to Page_DragDrop
    And I drag and drop element amount_5000 to element amount_target_1

  @mc-drag_and_drop_by_js
  Scenario: Negative to web URL
    Given I navigate to "https://jqueryui.com/droppable/"
    And I change the page spec to Page_DragDrop
#    And I drag and drop element amount_5000 to element amount_target_1 by javascript
    And I drag and drop element drag_from to element drop_To by javascript

  @mc-drag_and_drop_3
  Scenario: Negative to web URL
    Given I navigate to "https://demo.guru99.com/test/drag_drop.html"
    And I change the page spec to Page_DragDrop
    And I drag and drop element amount_5000 to element amount_target_1 by javascript