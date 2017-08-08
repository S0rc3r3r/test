  Feature: Test Muso Login Page
  @Regression @Login
  Scenario Outline: User should be able to login when valid credentials are provided or
  				error message will be displayed when invalid credentials are provided
  					
  		Given I navigate to Muso LogIn Page
  		When I enter userName '<username>'
  		And I enter password '<password>'
  		And I click on signIn
  		Then I '<can_login>' be able to login
  		
  		Examples:
  		|  username | password | can_login|
  		|  Tanase | 123 | should |
  		|  Izabella | 321 | should not|

 @Regression @ForgottenPassword
 Scenario: User should be able to reset password when valid user provided or
 				error message will be displayed when invalid user provided
  					
  		Given I navigate to Muso LogIn Page
  		When I enter userName 'validUser'
  		And I click on 'forgotten password link'
  		Then an email is sent
  		And I can reset password