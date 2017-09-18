  @DashBoard @Regression
  Feature: DashBoard functionality

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
  		
        
  @InfringementSummary
  Scenario: Verify Infringement Summary UI elements
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And 'Infringement Summary' option is selected
        When I count 'Total Removals' I get '820'
        When I count 'Last 2 Months' I get '258'
        When I count 'Removals Last Week' I get '90'
        

  @AntiPiracy
  Scenario: [C28] Verify Anti Piracy Links UI elements
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu
        Then 'Anti Piracy Links' report is displayed and has all elements displayed correctly
  
   @AntiPiracy2
   Scenario: [C29] Anti Piracy Links Date Range Filter
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu
        When I select 'Last Week' option from 'Date Range' menu
        Then Only the results that match 'Date Range' filter are displayed
  	
  @AntiPiracy3
   Scenario: [C30] Anti Piracy Links Campaign Filter
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu
        When I select 'Nerina Pallot - Finally' option from 'Campaign' menu
        And I select 'Nerina Pallot' option from 'Campaign' menu
        Then Only the results that match 'Campaign' filter are displayed in 'Removal Details' table
        
  @AntiPiracy4
  Scenario: [C33] Anti Piracy Links Table Filters
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        When I select 'Anti Piracy Links' option from 'Report' menu
        And I change the number of rows to be displayed for 'Removal Details' table to 100
        Then 'Removal Details' results are displayed on 1 page
        And The number of results displayed in table 'Removal Details' is 3
        
  	
  @Test     
  Scenario Outline: test Infringement summary count
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And 'Infringement Summary' option is selected
        When I count '<infringementSummary>' I get '<itemsDisplayed>'

  Examples:
    | infringementSummary | itemsDisplayed |
    |  Total Removals      |  820  |
    |  Last 2 Months       |  258  |
    |  Removals Last Week  |  90   |
         		

  	