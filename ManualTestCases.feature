Feature: SauceDemo Purchase Flow

  Scenario: TC01 - Successful purchase with valid checkout information
    Given the user is on the SauceDemo login page
    When the user enters "standard_user" as the username
    And enters "secret_sauce" as the password
    And selects the Login button
    Then the Products page should be displayed
    And the user is on the Products page
    When the user adds "Sauce Labs Backpack" to the cart
    Then the shopping cart badge should display "1"
    When the user opens the shopping cart
    Then "Sauce Labs Backpack" should be displayed with quantity 1 and price $29.99
    When the user clicks the Checkout button
    And enters "Test" as the first name
    And enters "User" as the last name
    And enters "1000" as the postal code
    And selects the Continue button
    Then the Checkout Overview page should be displayed
    And the item total should be $29.99
    And the tax should be $2.40
    And the order total should be $32.39
    When the user clicks the Finish button
    Then the message "Thank you for your order!" should be displayed
    When the user clicks the Back Home button
    And opens the shopping cart
    Then the shopping cart should be empty
    And the shopping cart badge should not be displayed

  Scenario: TC02 - Login with a locked-out user
    Given the user is on the SauceDemo login page
    When the user enters "locked_out_user" as the username
    And enters "secret_sauce" as the password
    And selects the Login button
    Then the user should remain on the login page
    And the error message "Epic sadface: Sorry, this user has been locked out." should be displayed

  Scenario: TC03 - Checkout with missing First Name
    Given the user is logged in as "standard_user"
    And the user has added "Sauce Labs Backpack" to the cart
    And the user is on the Checkout Information page
    When the user leaves the First Name field empty
    And enters "User" as the Last Name
    And enters "1000" as the Postal Code
    And selects the Continue button
    Then the error message "Error: First Name is required" should be displayed
    And the user should remain on the Checkout Information page

  Scenario: TC04 - Removing the only item from the cart
    Given the user is logged in as "standard_user"
    And the shopping cart is empty
    When the user adds "Sauce Labs Backpack" to the cart
    Then the shopping cart badge should display "1"
    When the user opens the shopping cart
    And removes "Sauce Labs Backpack"
    Then the shopping cart should be empty
    And the shopping cart badge should not be displayed
