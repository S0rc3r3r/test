  @DashBoard @Regression
  Feature: DashBoard functionality

  @Report
  Scenario: Verify that all Reports options are displayed based on user rights.
  		Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
  		When I expand 'Report' menu
  		Then I should see 4 available options: 'Anti Piracy Links,Submit Infringements,Market Analytics,Infringement Summary'
  		And 'Infringement Summary' option is selected
  		When I expand 'Date Range' menu
  		Then I should see 8 available options: 'All Time,Last 12 Months,Last 6 Months,Last 2 Months,Last Month,Last 4 Weeks,Last Week,Custom Range'
  		And 'Last 2 Months' option is selected
  		
  @Report
  Scenario: Verify Anti Piracy Links report
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        When I select 'Anti Piracy Links' option from 'Report' menu
        Then 'Anti Piracy Links' report is displayed and has all elements displayed correctly
        When I change the number of rows to be displayed for 'Removal Details' table to 100
        Then 'Removal Details' results are displayed on 1 page
        And The number of results displayed in table 'Removal Details' is 4
        
  @Report
  Scenario: Verify Infringement Summary report
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
  
   @AntiPiracy
   Scenario: [C29] Anti Piracy Links Date Range Filter
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu
        When I select 'Last Week' option from 'Date Range' menu
        Then Only the results that match 'Date Range' filter are displayed in 'Removal Details' table
  	
  @AntiPiracy
   Scenario: [C30] Anti Piracy Links Campaign Filter
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user
        And I select 'Anti Piracy Links' option from 'Report' menu
        When I select 'Nerina Pallot - Finally' option from 'Campaign' menu
        And I select 'Nerina Pallot' option from 'Campaign' menu
        Then Only the results that match 'Campaign' filter are displayed in 'Removal Details' table
        


  	
  	
  	
  	
  @DateRange
  Scenario: Verify that all Date Range options are present
  		Given I'm located on new dashboard page as 'NORMAL' user
  		When I click on 'Date Range' button
  		Then I should see 8 available options
  		And 'All Time' option is there
  		And 'Last 12 Months' option is there
  		And 'Last 6 Months' option is there
  		And 'Last 2 Months' option is there selected
  		And 'Last Month' option is there
  		And 'Last 4 Weeks' option is there
  		And 'Last Week' option is there
  		And 'Custom Range' option is there
  		

  	