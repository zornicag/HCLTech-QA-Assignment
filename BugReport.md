# Bug Report – Product Sorting Failure

**Bug ID:** BUG-001  
**Title:** Product sorting fails for all sorting options when logged in as error_user  
**Application:** SauceDemo  
**URL:** https://www.saucedemo.com/inventory.html  
**Test User:** error_user  
**Severity:** Medium  
**Priority:** Medium  
**Status:** New

## Preconditions
- The user is logged in with the error_user account.
- The Products page is displayed.

## Steps to Reproduce
1. Open https://www.saucedemo.com/.
2. Log in using:
    - Username: error_user
    - Password: secret_sauce
3. Open the product sorting dropdown.
4. Try each sorting option: Name (A to Z), Name (Z to A), 
Price (low to high), and Price (high to low).

## Expected Result
Products should be sorted according to the selected sorting option.

## Actual Result
The sorting option remains "Name (A to Z)" regardless of the selected option. 

A browser alert is displayed: "Sorting is broken! This error has been reported to Backtrace."

## Impact
The user cannot reliably sort products using the available sorting options.

## Severity and Priority Rationale
**Severity – Medium:** The sorting functionality is affected, but the product catalog remains accessible.

**Priority – Medium:** The defect affects product browsing and should be fixed, but it does not completely block access to the application.
