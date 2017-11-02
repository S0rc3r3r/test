@Regression @TypeFilter 
Feature: Type features 

Background: 
    Given I'm located on new dashboard page as 'bogdan.tanasoiu' user 
    
Scenario: [] Verify Type filter search box without results 
    When I search for 'Files' 'Type' 
    Then '1' results are displayed in 'Type' filter 
    
Scenario: [] Verify Type filter search box without results 
    When I search for 'xxxx' 'Type' 
    Then '0' results are displayed in 'Type' filter 
    
Scenario: [] Verify Type subcategories state when category is selected 
    When I select 'Files' option from 'Type' menu 
    Then All 'Files' subcategories from 'Type' menu are disabled 
 