package com.muso.selenium.base.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class UserActions {

    public static void hoverMouse(final WebDriver driver, final WebElement element) {
        Action builder;
        final Actions hover = new Actions(driver);
        hover.moveToElement(element);
        builder = hover.build();
        hover.perform();
        builder.perform();
    }

}
