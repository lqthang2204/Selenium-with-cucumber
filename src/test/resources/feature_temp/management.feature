@feature-mc-21946 @notification @casereminder @smoke @aou @regression
Feature: Case Management Reminder Notification
  As a user
  I should be able to receive the follow up reminder notification
  I should be able navigate to engagement list View when click on follow up reminder notification
  I should be able navigate to profile when click on follow up reminder notificationBackground:
  Background:
        Given I navigate to "https://pmt-dho.qak8s.vibrenthealth.com/"
        And I change the page spec to AdminLoginPage
        And I perform fill-admin-sign-in-form action with override values
              |username-input | mcadmin@vibrenthealth.com |
              |password-input | Password123$              |
        And I change the page spec to HomePagePMT
        And I wait for element expand-menu to be DISPLAYED
            # Create prospect in automation site 3
        And I perform change-to-program-manager-role action
        And I click element prospect-management-icon
        And I change the page spec to ProspectManagementPage
        And I wait for element spinner to be NOT_DISPLAYED
        And I click element add-prospect-button
        And I become a random user
        And I change the page spec to AddEditProspectPage
        And I type "TA-FIRST-NAME" into element first-name-input
        And I type "TA-LAST-NAME" into element last-name-input
        And I type "USER.email" into element email-input
        And I save text for element email-input with key "prospect.email"
        And I type "USER.phoneNumber" into element mobile-phone-input
        And I type "UNIQUE.Automates" into element list-name-input
        And I save text for element list-name-input with key "automation-created-list"
        And I click keyboard Enter button on element list-name-input
        And I wait for element edit-org-button to be DISPLAYED
        And I wait for element save-button to be DISPLAYED
        And I wait for element edit-org-button to be ENABLED
        And I click element edit-org-button
        And I click element oss-title with text "Site"
        And I click element oss-view-all-button with text "Site"
        And I click element oss-radio-option with text "Automation Site 3"
        And I click element oss-save-button
        And I wait for element save-button to be DISPLAYED
        And I click element save-button
        And I change the page spec to ProspectManagementPage
        And I wait for element success-alert to be DISPLAYED
            # Create PMT user in automation site 3 for assignee in case list
        And I change the page spec to HomePagePMT
        And I wait for element user-admin-icon to be ENABLED
        And I click element user-admin-icon
        And I become a random user
        And I run User-Creation.postman_collection.json postman collection with UserCreationData.json data file
        And I change the page spec to UserAdminPage
        And I perform search-user action
        And I change the page spec to AddEditUserPage
        And I click element edit-user-button
        And I save text for element email-address-value with key "pmt-first-user.email"
        #    And I click element oss-edit-button
        #    And I click element oss-title with text "Awardee"
        #    And I click element oss-view-all-button with text "Awardee"
        #    And I click element oss-radio-option with text "Select All"
        #    And I click element oss-title with text "Site"
        #    And I click element oss-view-all-button with text "Site"
        And I click element all-of-us
        And I click element automation-site-3
        #    And I click element oss-save-button
        And I click element save-button
        And I wait for element success-alert to be DISPLAYED
        And I click element back-to-user-management
        And I change the page spec to UserAdminPage
        And I wait for element search-input to be ENABLED
            # Create 1-on-1 prospect segmentation
        And I change the page spec to HomePage
        And I click element communications-icon
        And I wait for element spinner to be NOT_DISPLAYED
        And I change the page spec to SegmentationPage
        And I wait for element search-input to be DISPLAYED
        And I wait for element filter-button to be ENABLED
        And I wait for element create-segmentation-button to be ENABLED
        And I click element create-segmentation-button
        And I change the page spec to CreateSegmentationParticipantPage
        And I type "UNIQUE.TA-ListNameSegment 1-on-1" into element segmentation-name-input
        And I type "Create a 1-on-1 prospects segment for engagement" into element description-input
        And I perform create-1-on-1-prospects-segmentation action
        And I click element value-input-field
        And I type "KEY.automation-created-list" into element value-input-field
        And I wait for element loading-text to be NOT_DISPLAYED
        And I click keyboard Enter button on element value-input-field
        And I change the page spec to CreateSegmentationPage
        And I perform click-save-button action
        And I change the page spec to CreateSegmentationResultPage
        And I wait for element segmentation-name to be DISPLAYED
            # Create new engagement list
        And I change the page spec to CommunicationsPage
        And I wait for element case-management-tab to be ENABLED
        And I click element case-management-tab
        And I change the page spec to CaseManagementPage
        And I wait for element create-engagement-button to be ENABLED
        And I click element create-engagement-button
        And I wait for element spinner to be NOT_DISPLAYED
        And I type "UNIQUE.TA-Active engagement list" into element engagement-name-input
        And I save text for element engagement-name-input with key "engagement.list.name"
        And I click element engagement-goal-drop-down
        And I click element first-engagement-goal
        And I click element oss-edit-button
        And I click element oss-title with text "Site"
        And I click element oss-view-all-button with text "Site"
        And I click element oss-radio-option with text "Automation Site 3"
        And I click element oss-title with text "Assignees"
        And I click element oss-view-all-button with text "Assignees"
        And I click element oss-radio-option with text "Select All"
        And I click element oss-save-button
        And I wait for element next-button to be ENABLED
        And I click element next-button
        And I wait for element spinner to be NOT_DISPLAYED
        And I verify the text for element step-title
        And I click element listname-segmentation
        And I click element next-button
        And I wait for element spinner to be NOT_DISPLAYED
        And I wait for element review-step-title to be DISPLAYED
        And I wait for element automation-awardee-2-level-title to be DISPLAYED
        And I wait for element engagement-goal-info to be DISPLAYED
        And I wait for element create-list to be ENABLED
        And I click element create-list
        And I wait for element success-alert to be DISPLAYED
        And I wait for element spinner to be NOT_DISPLAYED
        And I click element search-engagement
        And I type "KEY.engagement.list.name" into element search-engagement
        And I wait for element loading-indicator to be NOT_DISPLAYED
        And I verify the text for element engagement-on-list is "KEY.engagement.list.name"
        And I verify the text for element engagement-status-on-list is "Active"

        @mc-thang
        Scenario Outline: Verify follow up reminder notification is sent to program manager, cem, site manager, research assistant role when follow up recontact date is current date
        Given I click element engagement-on-list
            # Add new engagement log with current day, morning
        And I wait for element add-button to be ENABLED
        And I click element add-button
        And I change the page spec to AddEditEngagementPage
        And I wait for element spinner to be NOT_DISPLAYED
        And I wait for element save-engagement-button to be DISPLAYED
        And I wait for element type-of-engagement-dropdown to be DISPLAYED
        And I wait for element spinner to be NOT_DISPLAYED
        And I perform fill-engagement-history-fields action with override values
        | summary-textarea | UNIQUE.TA NOTE |
        And I scroll to element save-engagement-button
        And I wait for element time-set-up-checkbox to be DISPLAYED
        And I click element time-set-up-checkbox
        And I click element recontact-date-dropdown
        And I click element recontact-date-dropdown-option with text "Today"
        And I click element recontact-time-dropdown
        And I click element recontact-time-dropdown-option with text "<recontactTime>"
        And I perform save-engagement action
        And I change the page spec to ProfilePage
        And I wait for element success-alert to be DISPLAYED
              Examples:
                  | recontactTime                 |
                 | Morning (8:00 AM - 11:00 AM)  |
                    | Lunch (11:01 AM - 2:00 PM)    |
                  | Afternoon (2:01 PM - 5:00 PM) |
                   | Evening (5:01 PM - 8:00 PM)   |