  @Login
  Feature: Test Muso Login Page
  
  Scenario Outline: scenario description
  		Given I navigate to Muso LogIn Page
  		When I enter userName '<username>'
  		And I enter password '<password>'
  		And I click on signIn
  		Then I '<can_login>' be able to login
  		
  		Examples:
  		|  username | password | can_login|
  		|  Tanase | 123 | should |
  		|  Izabella | 321 | should not|

 