@Regression @Security 
Feature: Security features 

     
Scenario: [] Verify that user is not able to access dashboard using an expired token
    When I'm accessing dashboard using 'expired_token'
    Then I'm redirected to musoWeb Login Page

Scenario: [] Verify that user is not able to access dashboard using an invalid token
    When I'm accessing dashboard using 'invalid_token'
    Then I'm redirected to musoWeb Login Page
     
Scenario: [] Verify that user is not able to access dashboard if he has only MA Service Type and userId = 9200;
    When I'm accessing dashboard using '9200'    
    Then I'm redirected to musoWeb Login Page
      
Scenario: [] Verify that only Market Analytics report IS displayed when token contains only MA Service Type
    Given I'm located on new dashboard page as '9570' user
    When I expand 'Report' menu
    Then I see 1 available options in 'Report' menu filter:
        |Market Analytics|
    
Scenario: [] Verify that Market Analytics and Infringement Summary reports are displayed when token contains MA and CORE Service Type.
    Given I'm located on new dashboard page as '8909' user
    When I expand 'Report' menu
    Then I see 4 available options in 'Report' menu filter:
        | Infringement Summary |    
        | Anti Piracy Links    |
        | Submit Infringements |
        | Market Analytics     |
    
Scenario: [] Verify that Market Analytics report IS NOT displayed when token contains only MA Service Type and user is not of type 89*
    Given I'm located on new dashboard page as '7249' user    
    When I expand 'Report' menu
    Then I see 3 available options in 'Report' menu filter:
        | Infringement Summary |
        | Anti Piracy Links    |
        | Submit Infringements |

 Scenario: [] Verify that Market Analytics report IS NOT displayed when token does not contain MA Service Type and user is of type 89*
    Given I'm located on new dashboard page as '8780' user    
    When I expand 'Report' menu
    Then I see 3 available options in 'Report' menu filter:
        | Infringement Summary |
        | Anti Piracy Links    |
        | Submit Infringements |   
   
 Scenario: [] Verify that Market Analytics report IS displayed for a Super User that has MA Service Type and child users 8909,7249
    Given I'm located on new dashboard page as '9100' user     
    When I expand 'Report' menu
    Then I see 3 available options in 'Report' menu filter:
        | Infringement Summary |
        | Anti Piracy Links    |
        | Submit Infringements |
    
Scenario: [] Verify that Market Analytics report IS NOT displayed for a Super User that has MA Service Type and child users 9001,9002
    Given I'm located on new dashboard page as '9000' user      
    When I expand 'Report' menu
    Then I see 3 available options in 'Report' menu filter:
        | Infringement Summary |
        | Anti Piracy Links    |
        | Submit Infringements |

    
   
   