package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionTester.class);

    public RegionTester(WebDriver driver) {
        super(driver);
    }

    public void expandRegionMenu(boolean expand) {
        marketAnalyticsPage.expandRegionMenu(expand);
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
