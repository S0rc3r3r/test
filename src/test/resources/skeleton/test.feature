  @test
  Feature: test angularJS page
  
  Scenario Outline: scenario description
  		Given I navigate to https://angularjs.org/ 
  		When I enter name <param_2>
  		Then the result should be Hello <param_2>!
  		
  		Examples:
  		|  param_2 |
  		|  Tanase |
  		|  Izabella |

 