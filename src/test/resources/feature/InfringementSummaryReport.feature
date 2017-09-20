  @Regression @InfringementSummary
  Feature: Infringement Summary Report features
  
  Background:
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user


  @InfringementSummaryUI
  Scenario: [C23] Verify Infringement Summary UI Elements
        Then 'Infringement Summary' report is displayed and has all elements displayed correctly
 
 
  @InfringementSummaryCounters
  Scenario: [CXX] Infringement Summary results for Last 2 Months DateRange filter
        When 'Last 2 Months' option is selected
        And 'All types' option is selected
        And I count 'Total Removals' I get '820'
        And I count 'Last 2 Months' I get '258'
        And I count 'Removals Last Week' I get '90'
        And I count 'Removals By Type' - 'Files' I get '0.8'
        And I count 'All Time' for campaign 'Nerina Pallot' I get '105849' 
        And I count 'Last 2 Months' for campaign 'Nerina Pallot' I get '729' 
        And I count 'All Time' for campaign 'Year Of The Wolf' I get '3278' 
        And I count 'Last Week' for campaign 'Year Of The Wolf' I get '2' 
        And I count 'Removals By Status' - 'Complete' I get '0.0'
        
  @InfringementSummaryDateRangeFilter
  Scenario: [C24] Verify Infringement Summary Date Range filter
        When I select 'All Time' option from 'Report' menu
        And I count 'Total Removals' I get '820'
        And I count 'Removals Last Week' I get '90'      
        Then 'Custom removals' counter is not displayed
        
  @aaa
  Scenario: [C24] Verify Infringement Summary Date Range filter
        When I select 'All Time' option from 'Report' menu
        And I count 'Total Removals' I get '820'
        And I count 'Removals Last Week' I get '90'      
        Then 'Custom removals' counter is not displayed
               
        
  @Report
  Scenario: Verify default selections.
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        When I expand 'Report' menu
        Then I should see 4 available options: #'Anti Piracy Links,Submit Infringements,Market Analytics,Infringement Summary'
            | Anti Piracy Links    |
            | Submit Infringements |
            | Market Analytics     |
            | Infringement Summary |
        And 'Infringement Summary' option is selected
        When I expand 'Date Range' menu
        Then I should see 8 available options: # 'All Time,Last 12 Months,Last 6 Months,Last Month,Last 4 Weeks,Last Week,Custom Range'
            | All Time      |
            | Last 12 Months|
            | Last 6 Months |
            | Last 2 Months |
            | Last Month    |
            | Last 4 Weeks  |
            | Last Week     |
            | Custom Range  |
        And 'Last 2 Months' option is selected
         When I expand 'Campaign' menu
         Then I should see at least 2 available options
           And I count 'Narina Pallot' campaign stats I get '105,849' 
        
        