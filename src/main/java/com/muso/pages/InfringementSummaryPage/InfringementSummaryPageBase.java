package com.muso.pages.InfringementSummaryPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.DateRangeType;
import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.pages.General.AbstractBasePage;
import com.muso.persistence.PersistenceManager;
import com.muso.utils.list.ListUtils;
import com.muso.utils.thread.ThreadHandler;

public abstract class InfringementSummaryPageBase extends AbstractBasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfringementSummaryPageBase.class);
    protected PersistenceManager persistenceManager;

    // MENU BUTTONS
    @FindBy(css = "button[title='Report']")
    protected WebElement ReportMenuButton;

    @FindBy(css = "button[title='Date range']")
    protected WebElement DateRangeMenuButton;

    @FindBy(css = "button[title='Campaign']")
    protected WebElement CampaignMenuButton;

    @FindBy(css = "button[title='Type']")
    protected WebElement TypeMenuButton;

    @FindBy(css = "button[title='Product']")
    protected WebElement ProductMenuButton;

    @FindBy(css = "#links span")
    protected WebElement SupportButton;

    @FindBy(css = "#links a:nth-child(1)")
    protected WebElement AccountButton;

    @FindBy(css = "#links a:nth-child(2)")
    protected WebElement LogoutButton;

    // MENU OPTIONS HOLDERS
    @FindBy(css = "muso-report-filter ul.dropdown-menu.inner")
    protected WebElement reportOptionsHolder;

    @FindBy(css = "#period-container > div div.ranges")
    protected WebElement dateRangeOptionsHolder;

    @FindBy(css = "#period-container div.daterangepicker.dropdown-menu.opensleft")
    protected WebElement dateRangeOptionsPicker;

    @FindBy(css = "muso-campaign-filter ul.dropdown-menu.inner")
    protected WebElement campaignOptionsHolder;

    @FindBy(css = "muso-campaign-filter div.bs-searchbox")
    protected WebElement campaignSearchBox;

    @FindBy(css = "muso-filter-type ul.dropdown-menu.inner")
    protected WebElement typeOptionsHolder;

    @FindBy(css = "muso-filter-type div.bs-searchbox")
    protected WebElement typeSearchBox;

    public InfringementSummaryPageBase(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        persistenceManager = PersistenceManager.getInstance();
    }

    public boolean isMenuExpanded(MenuType menu) {
        switch (menu) {
        case REPORT:
            return isMenuExpanded(reportOptionsHolder);
        case DATE_RANGE:
            return isMenuExpanded(dateRangeOptionsPicker);
        case CAMPAIGN:
            return isMenuExpanded(campaignOptionsHolder);
        case PRODUCT:
            return isMenuExpanded(ProductMenuButton); // TODO update with the holder
        case TYPE:
            return isMenuExpanded(typeOptionsHolder);
        default:
            throw new InvalidArgumentException("Unknown report " + menu.getName());
        }
    }

    public void clickOnSupportButton() {
        LOGGER.debug("Clicking on Support button");
        SupportButton.click();
    }

    public void clickOnAccountButton() {
        LOGGER.debug("Clicking on Account button");
        AccountButton.click();
    }

    public void clickOnLogoutButton() {
        LOGGER.debug("Clicking on Logout button");
        LogoutButton.click();
    }

    public void collapseAllMenus() {
        if (isMenuExpanded(reportOptionsHolder)) {
            clickOnReportMenuButton();
            return;
        }
        if (isMenuExpanded(dateRangeOptionsPicker)) {
            clickOnDateRangeMenuButton();
            return;
        }
        if (isMenuExpanded(campaignOptionsHolder)) {
            clickOnCampaignMenuButton();
            return;
        }
        if (isMenuExpanded(ProductMenuButton)) {
            clickOnProductMenuButton();
            return;
        }
        if (isMenuExpanded(typeOptionsHolder)) {
            clickOnTypeMenuButton();
            return;
        }
    }

    public void clickOnTypeMenuButton() {
        LOGGER.debug("Clicking on Type menu button");
        TypeMenuButton.click();
    }

    public void clickOnDateRangeMenuButton() {
        LOGGER.debug("Clicking on Date Range menu button");
        DateRangeMenuButton.click();
    }

    public void clickOnCampaignMenuButton() {
        LOGGER.debug("Clicking on Campaign menu button");
        CampaignMenuButton.click();
    }

    public void clickOnReportMenuButton() {
        LOGGER.debug("Clicking on Report menu button");
        ReportMenuButton.click();
    }

    public void clickOnProductMenuButton() {
        LOGGER.debug("Clicking on Product menu button");
        ProductMenuButton.click();
    }

    public void setReport(String optionName) {
        LOGGER.debug("Selecting {} report", optionName);
        WebElement reportElement = driver.findElement(factory.cssContainingText("span.text", optionName));
        reportElement.click();
        PersistenceManager.getInstance().setReport(ReportType.fromString(optionName));

    }

    public void setCampaign(String optionName) {
        LOGGER.debug("Selecting {} campaign", optionName);
        ThreadHandler.sleep(500); // TODO fix this

        List<WebElement> campaignElement = campaignOptionsHolder.findElements(By.cssSelector("li"));

        for (WebElement element : campaignElement) {
            if (element.getText().equals(optionName)) {
                element.click();
                PersistenceManager.getInstance().addCampaign(optionName);
                break;
            }
        }

    }

    public void setDateRange(String optionName) {

        LOGGER.debug("Selecting {} date range", optionName);

        WebElement dateRangeElement = dateRangeOptionsHolder.findElement(factory.cssContainingText("li", optionName));
        dateRangeElement.click();
        PersistenceManager.getInstance().setDateRange(DateRangeType.fromString(optionName));

    }

    public void setProduct(String optionName) {

        LOGGER.debug("Selecting {} product", optionName);
        PersistenceManager.getInstance().addProduct(optionName);
        throw new InvalidArgumentException("FUNCTIONALITY NOT IMPLEMENTED");
    }

    public void setType(String optionName) {

        LOGGER.debug("Selecting {} type", optionName);

        WebElement typeElement = typeOptionsHolder.findElement(factory.cssContainingText("li", optionName));
        typeElement.click();
        PersistenceManager.getInstance().setType(optionName);

    }

    protected boolean isMenuExpanded(WebElement menuElement) {

        if (Boolean.valueOf(menuElement.getAttribute("aria-expanded")) || Boolean.valueOf(menuElement.getAttribute("style").contains(
                "display: block"))) {
            return true;
        }
        return false;
    }

    public String getReportSelection() {

        if (!isMenuExpanded(MenuType.REPORT)) {
            collapseAllMenus();
            clickOnReportMenuButton();
        }

        WebElement menuSelection = null;

        try {
            menuSelection = reportOptionsHolder.findElement(By.className("selected"));
        } catch (NoSuchElementException ex) {
            LOGGER.warn("Report filter has no option selected");
            return "N/A";
        }

        LOGGER.debug("Report filter has the following options selected: {}", menuSelection.getText());

        return menuSelection.getText();
    }

    public ArrayList<String> getCampaignSelection() {

        if (!isMenuExpanded(MenuType.CAMPAIGN)) {
            collapseAllMenus();
            clickOnCampaignMenuButton();
        }

        ArrayList<String> selectedCampaigns = new ArrayList<String>();
        List<WebElement> selectedElements = campaignOptionsHolder.findElements(By.className("selected"));
        List<WebElement> selectedGroups = campaignOptionsHolder.findElements(By.className("disabled"));

        if (selectedElements.isEmpty()) {
            selectedCampaigns.add("All campaigns");
        } else {
            for (WebElement disabledItem : selectedGroups) {
                selectedCampaigns.add(disabledItem.getText());
            }

            for (WebElement selectedItem : selectedElements) {
                if (!selectedItem.getAttribute("class").equals("dropdown-header")) {
                    selectedCampaigns.add(selectedItem.getText());
                }
            }
        }

        LOGGER.info("Found {} campaigns selected: {}", selectedCampaigns.size(), selectedCampaigns.toString());

        return selectedCampaigns;
    }

    public String getDateRangeSelection() {

        if (!isMenuExpanded(MenuType.DATE_RANGE)) {
            collapseAllMenus();
            clickOnDateRangeMenuButton();
        }

        WebElement menuSelection = dateRangeOptionsHolder.findElement(By.className("active"));
        LOGGER.debug("Date Range filter has the following options selected: {}", menuSelection.getText());

        return menuSelection.getText();
    }

    public ArrayList<String> getProductSelection() {
        throw new InvalidArgumentException("NOT IMPLEMENTED");
    }

    public ArrayList<String> getTypeSelection() {

        if (!isMenuExpanded(MenuType.TYPE)) {
            collapseAllMenus();
            clickOnTypeMenuButton();
        }

        ArrayList<String> selectedTypes = new ArrayList<String>();
        List<WebElement> menuSelection = typeOptionsHolder.findElements(By.className("selected"));

        if (menuSelection.isEmpty()) {
            selectedTypes.add("All types");
        } else {
            for (WebElement element : menuSelection)
                selectedTypes.add(element.getText());
        }

        LOGGER.debug("Type filter has the following options selected: {}", selectedTypes.toString());

        return selectedTypes;
    }

    public void searchForTypeAndSelect(String optionName) {
        LOGGER.debug("Searching for {} option under Type", optionName);
        typeSearchBox.clear();
        typeSearchBox.sendKeys(optionName);

        List<WebElement> searchResults = typeOptionsHolder.findElements(By.cssSelector("li[class='']"));

        for (WebElement element : searchResults) {
            LOGGER.debug("Selecting {} option from the Region menu", element.getText());
            element.click();
        }

        WebElement selectedResult = typeOptionsHolder.findElement(By.cssSelector("li[class='active']"));
        LOGGER.debug("Selecting {} option from the Region menu", selectedResult.getText());
        selectedResult.click();
    }

    public void searchForCampaignAndSelect(String optionName) {
        LOGGER.debug("Searching for {} option under Type", optionName);
        campaignSearchBox.clear();
        campaignSearchBox.sendKeys(optionName);

        List<WebElement> searchResults = campaignOptionsHolder.findElements(By.cssSelector("li[class='']"));

        for (WebElement element : searchResults) {
            LOGGER.debug("Selecting {} option from the Camapign menu", element.getText());
            element.click();
        }

        WebElement selectedResult = campaignOptionsHolder.findElement(By.cssSelector("li[class='active']"));
        LOGGER.debug("Selecting {} option from the Campaign menu", selectedResult.getText());
        selectedResult.click();
    }

    public ArrayList<String> getCampaignOptions() {
        LOGGER.debug("Retrieving Campaign filter availabe options");
        ArrayList<String> optionsList = new ArrayList<String>();

        List<WebElement> campaignElements = campaignOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement element : campaignElements) {
            optionsList.add(element.getText());
        }

        LOGGER.debug(optionsList.toString());
        return optionsList;
    }

    public ArrayList<String> getReportOptions() {

        if (!isMenuExpanded(MenuType.REPORT)) {
            collapseAllMenus();
            clickOnReportMenuButton();
        }

        LOGGER.debug("Retrieving Report filter availabe options");
        ArrayList<String> optionsList = new ArrayList<String>();

        List<WebElement> reportElements = reportOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement element : reportElements) {
            optionsList.add(element.getText());
        }

        LOGGER.debug(optionsList.toString());
        return optionsList;
    }

    public ArrayList<String> getDateRangeOptions() {
        if (!isMenuExpanded(MenuType.DATE_RANGE)) {
            collapseAllMenus();
            clickOnDateRangeMenuButton();
        }

        LOGGER.debug("Retrieving Date Range filter availabe options");

        ArrayList<String> optionsList = new ArrayList<String>();

        List<WebElement> dateRangeElements = dateRangeOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement element : dateRangeElements) {
            optionsList.add(element.getText());
        }

        LOGGER.debug(optionsList.toString());
        return optionsList;

    }

    @Override
    public void checkUIElements() {
        super.checkUIElements();

        // Verify Report filter selection
        assertEquals(persistenceManager.getReport().getText() + " is not the default selection for Report.", persistenceManager.getReport().getText(),
                getReportSelection());

        // Verify Date Range filter selection
        assertEquals(persistenceManager.getDateRange() + " is not the default selection for DateRange.", persistenceManager.getDateRange(),
                getDateRangeSelection());

        // Verify Type filter selection
        assertTrue(persistenceManager.getType() + " is not the default selection for Type.", ListUtils.conpareArrays(getTypeSelection(),
                persistenceManager.getType()));

        // TODO Add Product filter validation

        // Verify Campaign filter selection and Header
        verifyCampaignFilterAndHeader();

        // Verify that selected Report is displayed in page Header
        assertEquals(persistenceManager.getReport().getText() + " is not displayed in page Header.", persistenceManager.getReport().getText(),
                getReportfromHeader());

        // Verify that selected Date Range is displayed in page Header
        assertEquals(persistenceManager.getDateRange() + " is not displayed in page Header.", persistenceManager.getDateRange(),
                getDateRangeFromHeader());

    }

    private void verifyCampaignFilterAndHeader() {
        ArrayList<String> selectedCampaigns = getCampaignSelection();
        assertTrue("The number of selected campaign is incorrect.", persistenceManager.getCampaigns().size() == selectedCampaigns.size());

        if (persistenceManager.getCampaigns().size() > 6) {
            assertEquals("'Multiple campaigns' is not displayed in header", "Multiple campaigns", getCampaignFromHeader());
        } else {
            if (persistenceManager.getCampaigns().size() > 0) {
                for (String expectedCampaign : persistenceManager.getCampaigns()) {
                    boolean isCampaignFound = false;
                    for (String selectedCampaign : selectedCampaigns) {
                        if (expectedCampaign.equals(selectedCampaign)) {
                            isCampaignFound = true;
                            break;
                        }
                    }

                    assertTrue(expectedCampaign + " should be a selected option under Campaign filter", isCampaignFound);
                }
            }
        }
    }

}
