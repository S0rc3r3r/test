package com.muso.pages;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.dataproviders.UserDataProvider;
import com.muso.enums.DateRangeType;
import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.enums.Table;
import com.muso.interfaces.IPageActions;
import com.muso.models.User;
import com.muso.persistence.MenuOptions;
import com.muso.testers.DashboardTester;
import com.muso.utils.RegexTools.RegExpTools;
import com.muso.utils.thread.ThreadHandler;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class AbstractPage implements IPageActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPage.class);
    protected WebDriver driver;
    protected ByAngular.Factory factory;
    protected NgWebDriver ngwd;
    protected DashboardTester dashBoardTester;

    // HEADERS
    @FindBy(css = "#header h4")
    @CacheLookup
    protected WebElement reportInfo;

    @FindBy(css = "h2.allCampaigns")
    @CacheLookup
    protected WebElement campaignInfo;

    @FindBy(css = "h2.dateRange")
    @CacheLookup
    protected WebElement dateRangeInfo;

    @FindBy(css = "#header img")
    protected WebElement logoImg;

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

    // INFRINGEMENT SUMMARY
    @FindBy(id = "counterAllTime")
    protected WebElement totalRemovals;

    @FindBy(id = "counterLastWeek")
    protected WebElement lastWeekRemoval;

    @FindBy(id = "counterCustomPeriod")
    protected WebElement customRemoval;

    // ANTI PIRACY LINKS
    @FindBy(css = "muso-infringements p")
    protected WebElement antiPiracy_tableTitle;

    @FindBy(css = "muso-infringements thead")
    protected WebElement antiPiracy_tableHeader;

    @FindBy(css = "muso-infringements tbody")
    protected WebElement antiPiracy_tableBody;

    @FindBy(css = "muso-infringements button.btn.dropdown-toggle.pager")
    protected WebElement antiPiracy_showNoOfRowsButton;

    @FindBy(css = "muso-infringements ul.dropdown-menu.inner")
    protected WebElement antiPiracy_showNoOfRowsOptions;

    @FindBy(css = "muso-infringements span.caret.caret-right")
    protected WebElement antiPiracy_nextPageButton;

    @FindBy(css = "muso-infringements span.caret.caret-left")
    protected WebElement antiPiracy_previousPageButton;

    @FindBy(css = "muso-infringements span[class='pages']")
    protected WebElement antiPiracy_currentPage;

    @FindBy(css = "muso-infringements")
    protected WebElement antiPiracy_pageHolder;

    protected User user;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;

        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
        PageFactory.initElements(driver, this);
    }

    public AbstractPage(WebDriver driver, String userFile) {
        this.driver = driver;

        final UserDataProvider userDataProvider = new UserDataProvider("/testdata/json/" + userFile + ".json");
        user = userDataProvider.getData();

        final String application_url = System.getProperty("application_url") + "/?token=" + user.getJwt_token();

        navigateTo(application_url);
        PageFactory.initElements(driver, this);
    }

    @Override
    public AbstractPage expandReportMenu() {
        collapseAllMenus();
        if (!isMenuExpanded(ReportMenuButton)) {
            clickOnReportMenuButton();
        }
        return this;
    }

    @Override
    public AbstractPage expandCampaignMenu() {
        collapseAllMenus();
        if (!isMenuExpanded(CampaignMenuButton)) {
            clickOnCampaignMenuButton();
            ThreadHandler.sleep(500); // TODO remove this
        }
        return this;
    }

    @Override
    public AbstractPage expandDateRangeMenu() {
        collapseAllMenus();
        if (!isMenuExpanded(dateRangeOptionsPicker)) {
            clickOnDateRangeMenuButton();
        }
        return this;
    }

    @Override
    public AbstractPage expandTypeMenu() {
        collapseAllMenus();
        if (!isMenuExpanded(TypeMenuButton)) {
            clickOnTypeMenuButton();
        }
        return this;
    }

    @Override
    public AbstractPage expandProductMenu() {
        collapseAllMenus();
        if (!isMenuExpanded(ProductMenuButton)) {
            clickOnProductMenuButton();
        }
        return this;
    }

    public void collapseAllMenus() {
        if (isMenuExpanded(dateRangeOptionsPicker)) {
            clickOnDateRangeMenuButton();
        }
        if (isMenuExpanded(ProductMenuButton)) {
            clickOnProductMenuButton();
        }
        if (isMenuExpanded(TypeMenuButton)) {
            clickOnTypeMenuButton();
        }
        if (isMenuExpanded(CampaignMenuButton)) {
            clickOnCampaignMenuButton();
        }
        if (isMenuExpanded(ReportMenuButton)) {
            clickOnReportMenuButton();
        }
    }

    public boolean isLogoDisplayed() {
        if (logoImg.getAttribute("src").contains("assets/images/logo.png"))
            return true;
        return false;
    }

    @Override
    public boolean isOptionSelected(String optionName) {

        boolean isOptionSelected = false;
        String actualSelection = "N/A";
        List<WebElement> options;

        MenuType menu = MenuType.getMenuFromOption(optionName);

        switch (menu) {
        case REPORT:
            expandReportMenu();
            options = reportOptionsHolder.findElements(By.cssSelector("li"));
            break;
        case DATE_RANGE:
            expandDateRangeMenu();
            options = dateRangeOptionsHolder.findElements(By.cssSelector("li"));
            break;
        case TYPE:
            expandTypeMenu();
            options = typeOptionsHolder.findElements(By.cssSelector("li"));
            break;

        default:
            expandCampaignMenu();
            options = campaignOptionsHolder.findElements(By.cssSelector("li"));
            menu = MenuType.CAMPAIGN;
            break;
        }

        for (WebElement option : options) {
            LOGGER.debug("Element {}, attribute {}", option.getText(), option.getAttribute("class"));
            if (option.getAttribute("class").equals("selected") || option.getAttribute("class").equals("active"))
                actualSelection = option.getText();

            if (option.getText().equals(optionName) && (option.getAttribute("class").equals("selected") || option.getAttribute("class").equals(
                    "active"))) {
                isOptionSelected = true;
                LOGGER.debug("{} is the selected option for {}", optionName, menu.getName());
                break;
            }

        }

        if (isOptionSelected && (menu == MenuType.REPORT) || menu == MenuType.DATE_RANGE || menu == MenuType.CAMPAIGN) {

            if (menu == MenuType.CAMPAIGN) {
                if (optionName.equals("All campaigns")) {

                }
            }

            return isSelectedOptionDisplayedInHeader(optionName, menu);

        } else {
            if (menu == MenuType.TYPE && optionName.equals("All types"))
                return true; // in this case the menu has no active selection
            if (menu == MenuType.CAMPAIGN && optionName.equals("All campaigns"))
                return isSelectedOptionDisplayedInHeader(optionName, menu); // in this case the menu has no active
                                                                            // selection

            LOGGER.warn("{} is not the selected option for {}. Current selection is: {}", optionName, menu.getName(), actualSelection);
        }

        return isOptionSelected;
    }

    public ArrayList<String> getCampaignOptions() {
        ArrayList<String> campaignList = new ArrayList<String>();
        List<WebElement> options = campaignOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement option : options) {
            campaignList.add(option.getText());
            LOGGER.debug("Added Campaign name: {} to campaign options list", option.getText());
        }

        return campaignList;

    }

    public ArrayList<String> getTableValues(String columnName) {
        final int columnIndex = Table.REMOVAL_DETAILS.getTableColumns().indexOf(columnName);

        ArrayList<String> columnValues = new ArrayList<String>();

        List<WebElement> tableRows = antiPiracy_tableBody.findElements(By.cssSelector("tr"));

        for (WebElement row : tableRows) {
            String columnValue = row.findElement(By.cssSelector("td:nth-child(" + (columnIndex + 1) + ")")).getText();
            LOGGER.debug("Found '{}' in {} from {} table", columnValue, columnName, Table.REMOVAL_DETAILS.getTableName());
            columnValues.add(columnValue);
        }

        return columnValues;

    }

    public boolean isOptionDisplayed(String option, String activePage) {

        boolean isOptionDisplayed = false;
        List<WebElement> options;

        switch (activePage) {
        case "Report":
            options = reportOptionsHolder.findElements(By.cssSelector("li"));
            break;
        case "Date Range":
            options = dateRangeOptionsHolder.findElements(By.cssSelector("li"));
            break;

        default:
            throw new InvalidArgumentException("Unknown option: " + activePage);
        }

        for (WebElement optionName : options) {
            if (optionName.getText().equals(option))
                isOptionDisplayed = true;
        }

        return isOptionDisplayed;

    }

    public void setReport(String optionName) {

        WebElement reportElement = driver.findElement(factory.cssContainingText("span.text", optionName));
        reportElement.click();
        MenuOptions.getInstance().setReport(ReportType.fromString(optionName));

        ngwd.waitForAngularRequestsToFinish();
    }

    public void setCampaign(String optionName) {

        List<WebElement> campaignElement = campaignOptionsHolder.findElements(By.cssSelector("li"));

        for (WebElement element : campaignElement) {
            if (element.getText().equals(optionName)) {

                element.click();

                MenuOptions.getInstance().setCampaign(optionName);
                break;
            }
        }

    }

    public void setDateRange(String optionName) {
        // TODO investigate why this is not working
        // WebElement dateRangeElement = driver.findElement(factory.cssContainingText("span.text", optionName));
        WebElement dateRangeElement = dateRangeOptionsHolder.findElement(factory.cssContainingText("li", optionName));
        dateRangeElement.click();
        MenuOptions.getInstance().setDateRange(DateRangeType.fromString(optionName));

        ngwd.waitForAngularRequestsToFinish();
    }

    private String getDateRangeSelection() {
        expandDateRangeMenu();
        WebElement dateRangeElement = dateRangeOptionsHolder.findElement(By.className("active"));
        return dateRangeElement.getText();

    }

    private String getReportSelection() {
        expandReportMenu();
        WebElement reportElement = reportOptionsHolder.findElement(By.className("selected"));
        return reportElement.getText();
    }

    private List<String> getCampaignSelections() {
        expandCampaignMenu();
        List<String> selectedCampaigns = new ArrayList<String>();
        List<WebElement> selectedElements = campaignOptionsHolder.findElements(By.className("selected"));
        List<WebElement> selectedGroups = campaignOptionsHolder.findElements(By.className("disabled"));

        for (WebElement disabledItem : selectedGroups) {
            selectedCampaigns.add(disabledItem.getText());
        }

        for (WebElement selectedItem : selectedElements) {
            if (!selectedItem.getAttribute("class").equals("dropdown-header")) {
                selectedCampaigns.add(selectedItem.getText());
            }
        }

        LOGGER.info("Found {} campaigns selected: {}", selectedCampaigns.size(), selectedCampaigns.toString());

        return selectedCampaigns;
    }

    public List<String> getMenuSelectedOptions(MenuType menu) {

        switch (menu) {

        case CAMPAIGN:
            return getCampaignSelections();

        case TYPE:

            break;
        case PRODUCT:
            break;
        default:
            break;
        }

        return null;
    }

    public String getMenuSelectedOption(MenuType menu) {

        switch (menu) {
        case REPORT:
            return getReportSelection();
        case DATE_RANGE:
            return getDateRangeSelection();
        case PRODUCT:
            break;
        default:
            break;
        }

        return null;

    }

    protected boolean isMenuExpanded(WebElement menuElement) {
        if (Boolean.valueOf(menuElement.getAttribute("aria-expanded")) || Boolean.valueOf(menuElement.getAttribute("style").contains(
                "display: block"))) {
            return true;
        }
        return false;
    }

    public List<String> getMenuAvailableOptions(MenuType type) {

        List<String> optionsList = new ArrayList<String>();

        switch (type) {
        case REPORT:
            expandReportMenu();
            List<WebElement> reportElements = reportOptionsHolder.findElements(By.cssSelector("li"));
            for (WebElement element : reportElements) {
                optionsList.add(element.getText());
            }
            break;

        case DATE_RANGE:
            expandDateRangeMenu();
            List<WebElement> dateRangeElements = dateRangeOptionsHolder.findElements(By.cssSelector("li"));
            for (WebElement element : dateRangeElements) {
                optionsList.add(element.getText());
            }
            break;
        case CAMPAIGN:
            expandCampaignMenu();
            List<WebElement> campaignElements = campaignOptionsHolder.findElements(By.cssSelector("li"));
            for (WebElement element : campaignElements) {
                optionsList.add(element.getText());
            }
            break;

        default:
            throw new InvalidArgumentException("Unknown option: " + type);
        }

        return optionsList;

    }

    public int getTablePages(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return antiPiracy_pageHolder.findElements(By.cssSelector("span.pages")).size();
        default:
            return 0;

        }
    }

    public int getTableRows(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return antiPiracy_tableBody.findElements(By.cssSelector("tr")).size();
        default:
            return 0;
        }
    }

    public List<WebElement> getTableColumns(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return antiPiracy_tableHeader.findElements(By.cssSelector("th"));
        default:
            return null;
        }
    }

    public boolean isTableHeaderValid(Table table) {

        switch (table) {
        case REMOVAL_DETAILS:
            final List<WebElement> columns = getTableColumns(table);
            if (table.getTableColumns().size() != columns.size())
                return false;
            for (WebElement colum : columns) {
                if (!table.getTableColumns().contains(colum.getText()))
                    return false;
            }

        default:
            return true;
        }
    }

    public Integer getTablePageSize(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            final String pageSizeDisplayedText = antiPiracy_showNoOfRowsButton.getText();
            String selectedPageSizeText = "N/A";
            antiPiracy_showNoOfRowsButton.click();

            for (WebElement pageSizeElement : antiPiracy_showNoOfRowsOptions.findElements(By.cssSelector("li"))) {
                if (pageSizeElement.getAttribute("class").equals("selected")) {
                    selectedPageSizeText = pageSizeElement.getText();
                    break;
                }
            }
            final String pageSize = RegExpTools.regExpExtractor(pageSizeDisplayedText, "(\\d+)");
            assertEquals(pageSize + " should be the selected option from pageSize.", pageSize, selectedPageSizeText);
            return Integer.valueOf(pageSize);

        default:
            throw new InvalidArgumentException("Unknown/Invalid table name: " + table.getTableName());
        }

    }

    public Integer getTableDisplayPage(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return Integer.valueOf(antiPiracy_currentPage.getText());
        default:
            throw new InvalidArgumentException("Unknown/Invalid table name: " + table.getTableName());
        }

    }

    // PRIVATE METHODS
    @Override
    public void navigateTo(final String dashBoard_Url) {
        LOGGER.info("Navigate to {}", dashBoard_Url);

        driver.get(dashBoard_Url);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();

    }

    private void clickOnTypeMenuButton() {
        TypeMenuButton.click();
        ngwd.waitForAngularRequestsToFinish();
    }

    private void clickOnDateRangeMenuButton() {
        DateRangeMenuButton.click();
        ngwd.waitForAngularRequestsToFinish();
    }

    private void clickOnCampaignMenuButton() {
        CampaignMenuButton.click();
        ngwd.waitForAngularRequestsToFinish();
    }

    private void clickOnReportMenuButton() {
        ReportMenuButton.click();
        ngwd.waitForAngularRequestsToFinish();
    }

    private void clickOnProductMenuButton() {
        ProductMenuButton.click();
        ngwd.waitForAngularRequestsToFinish();
    }

    protected WebElement waitForElementToBeClickable(final WebElement element) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {

        }

        return element;
    }

    private boolean isSelectedOptionDisplayedInHeader(String optionName, MenuType menuName) {
        // LOGGER.info("Verifying Header text");

        boolean isHeaderInfoCorrect = false;
        String actualTextDisplayed = "N/A";

        switch (menuName) {
        case REPORT:
            actualTextDisplayed = reportInfo.getText();
            if (actualTextDisplayed.equals(optionName))
                isHeaderInfoCorrect = true;
            break;
        case DATE_RANGE:
            actualTextDisplayed = dateRangeInfo.getText();
            if (actualTextDisplayed.equals(optionName))
                isHeaderInfoCorrect = true;
            break;
        case CAMPAIGN:
            actualTextDisplayed = campaignInfo.getText();
            if (actualTextDisplayed.equals(optionName))
                isHeaderInfoCorrect = true;
            break;

        default:
            throw new InvalidArgumentException("Unknown menu: " + menuName);
        }

        if (isHeaderInfoCorrect) {
            LOGGER.debug("{} is correctly didsplayed in Header", optionName, menuName);
        } else {
            LOGGER.warn("{} is not displayed in Header. Current text displayed for {} is: {}", optionName, menuName, actualTextDisplayed);
        }

        return isHeaderInfoCorrect;
    }

}
