@Regression @TypeFilter 
Feature: Type features 

Background: 
    Given I'm located on new dashboard page as '7249' user 
    
Scenario: [] Verify that a single Type option is found when using search box
    When I search for 'Files' 'Type' 
    Then '1' result is displayed in 'Type' filter 
    
Scenario: [] Verify that multiple Type options are found when using search box
    When I search for 'si' 'Type' 
    Then I see 2 available options in 'Type' menu filter:
        |Social Media Site|
        |Indexer or Search Site|
        
 Scenario: [] Verify that Type options can be selected after search
    When I search for 'si' 'Type' and select the results
    Then 'Social Media Site; Indexer or Search Site' options from 'Type' menu are selected
               
Scenario: [] Verify that no type options are found when searching for invalid type
    When I search for 'xxxx' 'Type' 
    Then '0' results are displayed in 'Type' filter 
    
Scenario: [] Verify Type subcategories state when category is selected 
    When I select 'Files' option from 'Type' menu 
    Then All 'Files' subcategories from 'Type' menu are disabled 
    
Scenario: [] Verify Type selection after removing one option 
    When I select 'Google; Mp3; Streaming' options from 'Type' menu 
    And I remove 'Streaming' option from 'Type' menu 
    Then 'Streaming' option from 'Type' menu is not selected 
    And 'Google; Mp3' options from 'Type' menu are selected
 