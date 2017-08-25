  @DashBoard
  Feature: DashBoard functionality

  @Reports
  Scenario: Verify that all Reports options are present
  		Given I'm located on dashboard page as 'ADMIN' user
  		When I click on 'Report' button
  		Then I should see 4 available options
  		And 'Anti Piracy Links' option is there
  		And 'Submit Infringements' option is there
  		And 'Market Analytics' option is there
  		And 'Infringement Summary' option is there selected
  	
  @DateRange
  Scenario: Verify that all Date Range options are present
  		Given I'm located on dashboard page as 'NORMAL' user
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
  		
  		
  	