package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;

public class PiracyCategoryTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(PiracyCategoryTester.class);

    public PiracyCategoryTester(WebDriver driver) {
        super(driver);
    }

    public void expandPiracyCategoryMenu() {

        if (!marketAnalyticsPage.isMenuExpanded(MenuType.CATEGORY)) {
            marketAnalyticsPage.collapseAllMenus();
            marketAnalyticsPage.clickOnPiracyCategoryButton();
        }
    }

    public void setPiracyCategory(String optionName) {
        marketAnalyticsPage.setPiracyCategory(optionName);
    }

    public ArrayList<String> getPiracyCategorySelection() {
        return marketAnalyticsPage.getPiracyCategorySelection();
    }

}
