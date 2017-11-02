@Regression @ProductFilter 
Feature: Product features 

Background: 
    Given I'm located on new dashboard page as 'bogdan.tanasoiu' user 
    
Scenario: [] Verify Product filter is disabled if no campaign is selected
    When I select '30 Days; Aker Shot' option from 'Campaign' menu 
    And I select '30 yawm; Aker Shot' option from 'Product' menu 
    When No 'Campaign' option is selected

    
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
    
    
    
