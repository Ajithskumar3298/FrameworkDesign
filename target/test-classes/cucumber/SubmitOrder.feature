
@tag
Feature: Purchase the order from an Ecommerce website
  I want to use this template for my feature file
  
  Background:
  Given I landed on Ecommerce page

 
  @Regression
  Scenario Outline: Possitive test of submitting the order
    Given Logged into the application with username <name> and password <password>
    When I Add <ProductName> to cart
    And Checkout <ProductName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on confirmation page

    Examples: 
      | name                   |  password     | ProductName    |
      | ajithkumar.s@gmail.com |  Ajith@1998   | ZARA COAT 3    | 
     
