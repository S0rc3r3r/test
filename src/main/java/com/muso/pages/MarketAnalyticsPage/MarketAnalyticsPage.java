package com.muso.pages.MarketAnalyticsPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.CategoryType;
import com.muso.enums.MenuType;
import com.muso.enums.RegionType;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPageBase;

public class MarketAnalyticsPage extends InfringementSummaryPageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketAnalyticsPage.class);

    // MENU BUTTONS
    @FindBy(css = "button[title='Region']")
    protected WebElement regionMenuButton;

    @FindBy(css = "button[title='Piracy Category']")
    protected WebElement piracyCategoryMenuButton;

    // MENU OPTIONS HOLDERS
    @FindBy(css = "muso-region-country-filter ul.dropdown-menu.inner")
    protected WebElement regiontOptionsHolder;

    @FindBy(css = "muso-region-country-filter .bs-searchbox input")
    protected WebElement regionSearchBox;

    @FindBy(css = "muso-site-classification-filter ul.dropdown-menu.inner")
    protected WebElement piracyCategoryOptionsHolder;

    public MarketAnalyticsPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

    }

    public void expandRegionMenu(boolean expand) {
        if (expand) {
            LOGGER.debug("Expanding Region menu filter");
            if (isMenuExpanded(MenuType.REGION)) {
                collapseAllMenus();
                clickOnRegionMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Region menu filter");
            if (isMenuExpanded(MenuType.REGION)) {
                clickOnRegionMenuButton();
            }
        }
    }

    public void expandPiracyCategoryMenu(boolean expand) {
        if (expand) {
            LOGGER.debug("Expanding Piracy Category menu filter");
            if (isMenuExpanded(MenuType.CATEGORY)) {
                collapseAllMenus();
                clickOnPiracyCategoryMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Piracy Category menu filter");
            if (isMenuExpanded(MenuType.CATEGORY)) {
                clickOnPiracyCategoryMenuButton();
            }
        }
    }

    public void clickOnRegionMenuButton() {
        LOGGER.debug("Clicking on Region button");
        regionMenuButton.click();
    }

    public void clickOnPiracyCategoryMenuButton() {
        LOGGER.debug("Clicking on Piracy Category button");
        piracyCategoryMenuButton.click();
    }

    public void searchForRegionAndSelect(String optionName) {
        LOGGER.debug("Searching for {} option under Region", optionName);
        regionSearchBox.clear();
        regionSearchBox.sendKeys(optionName);

        List<WebElement> searchResults = regiontOptionsHolder.findElements(By.cssSelector("li[class='']"));

        for (WebElement element : searchResults) {
            LOGGER.debug("Selecting {} option from the Region menu", element.getText());
            element.click();
        }

        WebElement selectedResult = regiontOptionsHolder.findElement(By.cssSelector("li[class='active']"));
        selectedResult.click();
    }

    public void setRegion(String optionName) {
        LOGGER.debug("Selecting {} from Region", optionName);
        WebElement regionElement = regiontOptionsHolder.findElement(By.cssSelector("li"));
        regionElement.click();
        persistenceManager.setRegion(RegionType.fromString(optionName));
    }

    public void setPiracyCategory(String optionName) {
        LOGGER.debug("Selecting {} from Piracy Category", optionName);
        WebElement categoryElement = piracyCategoryOptionsHolder.findElement(By.cssSelector("li"));
        categoryElement.click();
        persistenceManager.setCategory(CategoryType.fromString(optionName));
    }

    @Override
    public void collapseAllMenus() {
        super.collapseAllMenus();

        if (isMenuExpanded(regiontOptionsHolder)) {
            clickOnRegionMenuButton();
        }

        if (isMenuExpanded(piracyCategoryOptionsHolder)) {
            clickOnPiracyCategoryMenuButton();
        }
    }

    public ArrayList<String> getRegionSelection() {
        ArrayList<String> selections = new ArrayList<String>();
        List<WebElement> menuSelection = regiontOptionsHolder.findElements(By.className("selected"));

        for (WebElement element : menuSelection) {
            selections.add(element.getText());
        }
        LOGGER.debug("Region filter has the following options selected: {}", selections.toString());
        return selections;

    }

    public ArrayList<String> getPiracyCategorySelection() {
        ArrayList<String> selections = new ArrayList<String>();
        List<WebElement> menuSelection = piracyCategoryOptionsHolder.findElements(By.className("selected"));

        for (WebElement element : menuSelection) {
            selections.add(element.getText());
        }
        LOGGER.debug("Piracy Category filter has the following options selected: {}", selections.toString());
        return selections;

    }

    @Override
    public String getReportSelection() {
        collapseAllMenus();
        return super.getReportSelection();
    }

    @Override
    public String getDateRangeSelection() {
        collapseAllMenus();
        return super.getDateRangeSelection();
    }

    @Override
    public ArrayList<String> getProductSelection() {
        collapseAllMenus();
        return super.getProductSelection();
    }

    @Override
    public ArrayList<String> getTypeSelection() {
        collapseAllMenus();
        return super.getTypeSelection();
    }

    @Override
    public ArrayList<String> getCampaignOptions() {
        collapseAllMenus();
        return super.getCampaignOptions();
    }

    @Override
    public void checkUIElements() {
        throw new InvalidArgumentException("NOT IMPLEMENTED");
    }

}
