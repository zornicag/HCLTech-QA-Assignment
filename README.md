# HCLTech QA Engineer Homework Assignment

## Project Overview

This project contains manual test scenarios, an exploratory testing bug report, and UI automation for the SauceDemo
application.

Application: https://www.saucedemo.com/

## Technologies

- Java 17
- Playwright for Java
- Maven
- IntelliJ IDEA
- Gherkin

## Project Files

- `ManualTestCases.feature` – Four manual test scenarios written in Gherkin syntax.
- `BugReport.md` – A reproducible product sorting defect.
- `SauceDemoTest.java` – Playwright UI automation.
- `pom.xml` – Maven project configuration and dependencies.

## Automated Test Scenario

The automation performs the following steps:

1. Retrieve the standard user credentials from the login page.
2. Log in with the standard user.
3. Identify and add the highest-priced and lowest-priced products.
4. Remove the highest-priced product and verify the remaining item.
5. Sort products by Name (A to Z) and add the last product.
6. Proceed to checkout.
7. Enter checkout information and complete the order.
8. Verify the order confirmation.
9. Verify that the shopping cart is empty.
10. Log out.

## Prerequisites

- Java JDK 17
- Maven
- Internet connection

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Allow Maven to download the dependencies.
4. Open the Terminal in IntelliJ IDEA and run the following command to install the Playwright Chromium browser:

   ```bash
   mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
   ```

5. Open `src/main/java/com/hcltech/qa/SauceDemoTest.java`.
6. Run the `main()` method.

## Test Results

The automation scenario was executed successfully during development.

The console displays verification messages for login, cart operations, order completion, empty cart, and logout.

## Notes

The Gherkin scenarios are intended for manual test design only and do not require Cucumber or SpecFlow execution.
