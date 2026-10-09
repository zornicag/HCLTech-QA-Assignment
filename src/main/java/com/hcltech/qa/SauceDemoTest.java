package com.hcltech.qa;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class SauceDemoTest {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
            );

            Page page = browser.newPage();

            page.navigate("https://www.saucedemo.com/");

            String usernames = page.locator("#login_credentials").innerText();
            String passwordInfo = page.locator(".login_password").innerText();

            String username = usernames.lines()
                    .filter(line -> line.trim().equals("standard_user"))
                    .findFirst()
                    .orElseThrow();

            String password = passwordInfo.lines()
                    .filter(line -> line.trim().equals("secret_sauce"))
                    .findFirst()
                    .orElseThrow();

            page.locator("#user-name").fill(username);
            page.locator("#password").fill(password);
            page.locator("#login-button").click();

            page.waitForURL("**/inventory.html");

            System.out.println("Login successful!");

            Locator products = page.locator(".inventory_item");

            double highestPrice = Double.NEGATIVE_INFINITY;
            double lowestPrice = Double.POSITIVE_INFINITY;

            String highestPriceProduct = "";
            String lowestPriceProduct = "";

            for (int i = 0; i < products.count(); i++) {
                Locator product = products.nth(i);

                String name = product.locator(".inventory_item_name").innerText();
                String priceText = product.locator(".inventory_item_price").innerText();

                double price = Double.parseDouble(priceText.replace("$", ""));

                if (price > highestPrice) {
                    highestPrice = price;
                    highestPriceProduct = name;
                }

                if (price < lowestPrice) {
                    lowestPrice = price;
                    lowestPriceProduct = name;
                }
            }

            System.out.println("Highest price: " + highestPriceProduct + " - $" + highestPrice);
            System.out.println("Lowest price: " + lowestPriceProduct + " - $" + lowestPrice);

            page.locator(".inventory_item")
                    .filter(new Locator.FilterOptions()
                            .setHasText(highestPriceProduct))
                    .getByRole(AriaRole.BUTTON,
                            new Locator.GetByRoleOptions().setName("Add to cart"))
                    .click();

            page.locator(".inventory_item")
                    .filter(new Locator.FilterOptions()
                            .setHasText(lowestPriceProduct))
                    .getByRole(AriaRole.BUTTON,
                            new Locator.GetByRoleOptions().setName("Add to cart"))
                    .click();

            String cartCount = page.locator(".shopping_cart_badge").innerText();

            if (!cartCount.equals("2")) {
                throw new AssertionError("Expected 2 items, but found: " + cartCount);
            }

            System.out.println("Two products added successfully!");

            page.locator(".shopping_cart_link").click();

            Locator cartItems = page.locator(".cart_item");

            Locator expensiveItem = cartItems.filter(
                    new Locator.FilterOptions().setHasText(highestPriceProduct)
            );

            expensiveItem.locator("button").click();

            if (page.locator(".cart_item").count() != 1) {
                throw new AssertionError("Expected exactly one item in the cart");
            }

            String remainingProduct = page.locator(".cart_item .inventory_item_name").innerText();

            if (!remainingProduct.equals(lowestPriceProduct)) {
                throw new AssertionError("Incorrect product remains in the cart");
            }

            System.out.println("Highest-priced product removed successfully!");
            System.out.println("Remaining product: " + remainingProduct);

            page.locator("#continue-shopping").click();

            page.locator(".product_sort_container").selectOption("az");

            Locator sortedProducts = page.locator(".inventory_item");
            Locator lastProduct = sortedProducts.last();

            String lastProductName = lastProduct
                    .locator(".inventory_item_name").innerText();

            lastProduct.locator("button").click();

            System.out.println("Last product added: " + lastProductName);

            String updatedCartCount = page.locator(".shopping_cart_badge").innerText();

            if (!updatedCartCount.equals("2")) {
                throw new AssertionError("Expected 2 products in the cart");
            }

            System.out.println("Cart contains 2 products!");

            page.locator(".shopping_cart_link").click();
            page.locator(".cart_item").first().waitFor();

// Verify both products are in the cart
            int cartItemsCount = page.locator(".cart_item").count();

            System.out.println("Products found in cart: " + cartItemsCount);

            if (cartItemsCount != 2) {
                throw new AssertionError(
                        "Expected 2 products before checkout, found: " + cartItemsCount
                );
            }

// Checkout information
            page.locator("#checkout").click();

            page.locator("#first-name").fill("Test");
            page.locator("#last-name").fill("User");
            page.locator("#postal-code").fill("1000");
            page.locator("#continue").click();

// Complete the order
            page.locator("#finish").click();

// Verify order confirmation
            String confirmation = page.locator(".complete-header").innerText();

            if (!confirmation.equals("Thank you for your order!")) {
                throw new AssertionError("Order confirmation not displayed");
            }

            System.out.println("Order completed successfully!");

// Return to Products and verify empty cart
            page.locator("#back-to-products").click();
            page.locator(".shopping_cart_link").click();

            if (page.locator(".cart_item").count() != 0) {
                throw new AssertionError("Cart is not empty after checkout");
            }

            System.out.println("Cart is empty after checkout!");

// Logout
            page.locator("#react-burger-menu-btn").click();
            page.locator("#logout_sidebar_link").click();

            page.waitForURL("https://www.saucedemo.com/");

            System.out.println("Logout successful!");

            page.waitForTimeout(2000);

            browser.close();
        }
    }
}

