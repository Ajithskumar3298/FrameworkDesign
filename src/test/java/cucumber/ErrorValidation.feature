
@tag
Feature: Error Validation
  I want to use this template for my feature file


  @ErrorValidation
  Scenario Outline: Negative test of Error Validation
    Given I landed on Ecommerce page
    When Logged into the application with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

       Examples: 
      | name                   |  password     |
      | ajithkumar.s@gmail.com |  Ajith@19  |
