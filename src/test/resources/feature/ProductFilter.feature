@Regression @ProductFilter 
Feature: Product features 

Background: 
    Given I'm located on new dashboard page as '7249' user 
    
Scenario: [] Verify Product filter is disabled if no campaign is selected 
    Then 'Product' menu filter is disabled 
    
Scenario: [] Verify Product filter is enabled if at least one campaign is selected 
    And 'Product' menu filter is disabled 
    When I select '30 Days' option from 'Campaign' menu 
    Then 'Product' menu filter is enabled 
    
Scenario: [] Verify Product filter is disabled when there is no campaign selected
    And 'Product' menu filter is disabled 
    And I select '30 Days' option from 'Campaign' menu 
    And 'Product' menu filter is enabled 
    When I remove '30 Days' option from 'Campaign' menu 
    Then 'Product' menu filter is disabled 
    
Scenario: [] Verify associated product is removed when campaign is removed 
    When I select '30 Days; Aker Shot' option from 'Campaign' menu 
    And I select '30 yawm; Aker Shot' option from 'Product' menu 
    When I remove '30 Days' option from 'Campaign' menu 
    Then '30 yawm' option from 'Product' menu is not selected 
    
Scenario: [] Verify Product selection after removing one option 
    Given I select 'Films' option from 'Campaign' menu 
    When I select 'Chaos, Disorder; Vuk; Theeb' option from 'Product' menu 
    And I remove 'Theeb' option from 'Product' menu 
    Then 'Theeb' option from 'Product' menu is not selected 
    
Scenario: [] Verify Product filter search box with results 
    When I select 'Films' option from 'Campaign' menu 
    And I search for 'Chaos' 'Product' 
    Then '1' results are displayed in 'Product' filter 
    
Scenario: [] Verify Product filter search box without results 
    When I select 'Films' option from 'Campaign' menu 
    And I search for 'xxxx' 'Product' 
    Then '0' results are displayed in 'Product' filter 
    
Scenario: [] Verify that Product options can be selected after search
    Given I select 'Films' option from 'Campaign' menu 
    When I search for 'in' 'Product' and select the results
    Then 'Coming Forth by Day; Dad Behind The Tree; Nightingale\'s Nest' options from 'Product' menu are selected
    
    
    
