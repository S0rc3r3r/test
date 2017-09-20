  @Regression @AntiPiracy
  Feature: AntiPiracy Report features
  
  Background:
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu


  @AntiPiracyUI
  Scenario: [C28] Verify Anti Piracy Links UI elements
        Then 'Anti Piracy Links' report is displayed and has all elements displayed correctly
  
   @AntiPiracyDateRangeFilter
   Scenario: [C29] Anti Piracy Links Date Range Filter
        When I select 'Last Week' option from 'Date Range' menu
        Then Only the results that match 'Date Range' filter are displayed
  	
  @AntiPiracyCampaignFilter
   Scenario: [C30] Anti Piracy Links Campaign Filter
        When I select 'Nerina Pallot - Finally' option from 'Campaign' menu
        And I select 'Nerina Pallot' option from 'Campaign' menu
        Then Only the results that match 'Campaign' filter are displayed
  
  @AntiPiracyTableFilter
  Scenario: [C33] Anti Piracy Links Table Filters
        When I change the number of rows to be displayed for 'Removal Details' table to 100
        Then 'Removal Details' results are displayed on 1 page
        And The number of results displayed in table 'Removal Details' is 4
        
  

  	