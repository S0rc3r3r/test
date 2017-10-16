@Regression @SubmitInfringements
  Feature: Submit Infringements features
  
  Background:
        Given I'm located on new dashboard page as 'bogdan.tanasoiu' user     
        And I select 'Submit Infringements' option from 'Report' menu


  @SubmitInfringementsUI
  Scenario: [CXX] Verify Submit Infringements UI Elements
        Then 'Submit Infringements' report is displayed and has all elements displayed correctly
