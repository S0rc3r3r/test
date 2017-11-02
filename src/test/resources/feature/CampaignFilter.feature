@Regression @CampaignFilter 
Feature: Campaigm features 

Background: 
    Given I'm located on new dashboard page as 'bogdan.tanasoiu' user 
    
Scenario: [] Verify Campaign subcategories state when category is selected 
    When I select 'Films' option from 'Campaign' menu 
    Then All 'Films' subcategories from 'Campaign' menu are disabled 
    
Scenario: [] Verify Campaign selection after removing one option 
    Given I select 'Chaos, Disorder - 2012; 30 Days; Aker Shot' option from 'Campaign' menu 
    When I remove 'Aker Shot' option from 'Campaign' menu 
    Then 'Aker Shot' option from 'Campaign' menu is not selected 
    
Scenario: [] Verify Campaign filter search box with results 
    When I search for 'Chaos' 'Campaign' 
    Then '1' results are displayed in 'Campaign' filter 
    
Scenario: [] Verify Campaign filter search box without results 
    When I search for 'Chaoooooos' 'Campaign' 
    Then '0' results are displayed in 'Campaign' filter