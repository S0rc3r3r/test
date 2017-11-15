@Regression @MembersFilter 
Feature: Members features 

Background:  
    Given I'm located on new dashboard page as 'super.user' user 
    
Scenario: [] Verify Member filter visibility when using a token that have childUsers
    Then 'Members' menu filter is visible 
    
Scenario: [] Verify Member filter visibility when using a token that does not have childUsers
    Given I'm located on new dashboard page as '7249' user 
    Then 'Members' menu filter is hidden

Scenario: [] Verify Member filter is displaying childUsers information
    When I expand 'Members' menu
    Then I should see 4 available options:
        |All members|
        |E-Reads Sales|
        |MBC Group|
        |Market Analytics|

@Test     
Scenario: [] Verify Member filter visibility when using a token that have childUsers
    When I select 'MBC Group' option from 'Members' menu
    Then 'Campaign' menu filter is enabled
    And Only the results that match 'Members' filter are displayed 


     