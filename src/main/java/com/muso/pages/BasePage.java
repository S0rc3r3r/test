package com.muso.pages;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage navigateTo() {
        driver.get("https://web.muso.com");
        assertEquals("Login - MUSO", driver.getTitle());
        return new LoginPage(driver);
    }

}