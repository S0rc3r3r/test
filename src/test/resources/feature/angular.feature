  @angular
  Feature: Test AngularJs Page
  
  Scenario Outline: scenario description
  		Given I navigate to AngularJs page
  		When I enter name '<username>'
  		Then The massage should be 'Hello <username>!'
  		
  		Examples:
  		|  username |
  		|  Tanase | 
  		|  Izabella | 
 