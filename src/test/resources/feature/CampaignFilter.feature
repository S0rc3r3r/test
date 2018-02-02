@Regression @CampaignFilter 
Feature: Campaigm features 

Background: 
    Given I'm located on new dashboard page as '7249' user 
     
Scenario: [] Verify Campaign filter is disabled when no user member is selected
    Given I'm located on new dashboard page as 'super.user' user 
    Then 'Campaign' menu filter is disabled 
    And 'Product' menu filter is disabled 
    
Scenario: [] Verify Campaign subcategories state when category is selected 
    When I select 'Films' option from 'Campaign' menu 
    Then All 'Films' subcategories from 'Campaign' menu are disabled 
    
Scenario: [] Verify that the selected campaign is disabled when category is selected
    Given I search for 'Maxi' 'Campaign'
    And I select 'Tannoura Maxi - 2012' option from 'Campaign' menu
    When I select 'Films' option from 'Campaign' menu
    Then All 'Films' subcategories from 'Campaign' menu are disabled 
    
Scenario: [] Verify Campaign selection after removing one option 
    Given I select 'Chaos, Disorder - 2012; 30 Days; Aker Shot' options from 'Campaign' menu 
    When I remove 'Aker Shot' option from 'Campaign' menu 
    Then 'Aker Shot' option from 'Campaign' menu is not selected 

@Mobile    
Scenario: [] Verify Campaign filter search box with results 
    When I search for 'Chaos' 'Campaign' 
    Then '1' results are displayed in 'Campaign' filter 
    
@Mobile     
Scenario: [] Verify Campaign filter search box without results 
    When I search for 'Chaoooooos' 'Campaign' 
    Then '0' results are displayed in 'Campaign' filter
    
@Mobile        
Scenario: [] Verify that Campaign options can be selected after search
    When I search for '2014' 'Campaign' and select the results
    Then 'Dahr Footmen - 2014; Theeb - 2014' options from 'Campaign' menu are selected
    
Scenario: [] Verify that a campaign can be selected on Campaign filter by clicking on campaign name from Campaign table
    When I select 'Love for Rent' from 'Campaigns' table
    Then 'Love for Rent;  حب للايجار; hubb lilayijar; Hob lel2egar' are displayed in 'Products' table
    And 'Love for Rent' option from 'Campaign' menu is selected 