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
    And I count 'Total Removals' I get '421873' 
    And I count 'Last 2 Months' I get '27896' 
    And I count 'Removals Last Week' I get '2849' 
    And I count 'Removals By Type' - 'Files' I get '5.9' 
    And I count 'Removals By Type' - 'Search Engine Delistings' I get '94.1' 
    And I count 'All Time' for campaign 'Love for Rent' I get '42245' 
    And I count 'Last 2 Months' for campaign 'Cesur ve Güzel' I get '4873' 
    And I count 'Removals By Status' - 'Complete' I get '84.4' 
    And I count 'Removals By Status' - 'In Progress' I get '15.6' 
    
@InfringementSummaryDateRangeFilter 
Scenario: [C24] Verify Infringement Summary Date Range filter 
    When I select 'Last Week' option from 'Date Range' menu 
    #Then 'All Time' is displayed in header
    And 'Custom removals' counter is not displayed 
    And I count 'Total Removals' I get '820' 
    And I count 'Removals Last Week' I get '90' 
    And Only the results that match 'Date Range' filter are displayed 
    
    
@aaa 
Scenario: [C24] Verify Infringement Summary Date Range filter 
    When I select 'All Time' option from 'Report' menu 
    And I count 'Total Removals' I get '820' 
    And I count 'Removals Last Week' I get '90' 
    Then 'Custom removals' counter is not displayed 
    
    
    
        