@Regression @CampaignFilter 
Feature: Campaigm features 

Background: 
    Given I'm located on new dashboard page as '7249' user 
     
Scenario: [] Verify Campaign filter is disabled when All Members option is selected under Members filter
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
    
Scenario: [] Verify Campaign filter search box with results 
    When I search for 'Chaos' 'Campaign' 
    Then '1' results are displayed in 'Campaign' filter 
    
Scenario: [] Verify Campaign filter search box without results 
    When I search for 'Chaoooooos' 'Campaign' 
    Then '0' results are displayed in 'Campaign' filter
    
