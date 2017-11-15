package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;

public class RegionTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionTester.class);

    public RegionTester(WebDriver driver) {
        super(driver);
    }

    public void expandRegionMenu() {

        if (!marketAnalyticsPage.isMenuExpanded(MenuType.REGION)) {
            marketAnalyticsPage.collapseAllMenus();
            marketAnalyticsPage.clickOnRegionButton();
        }
    }

    public void setRegion(String optionName) {
        marketAnalyticsPage.setRegion(optionName);
    }

    public void searchForRegionAndSelect(String optionName) {
        marketAnalyticsPage.searchForRegionAndSelect(optionName);
    }

    public ArrayList<String> getRegionSelection() {
        return marketAnalyticsPage.getRegionSelection();
    }
}
