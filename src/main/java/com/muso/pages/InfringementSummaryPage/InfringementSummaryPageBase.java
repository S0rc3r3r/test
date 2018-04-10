package com.muso.pages.InfringementSummaryPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.DateRangeType;
import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.pages.General.AbstractBasePage;
import com.muso.selenium.base.utils.Clicker;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;
import com.muso.utils.list.ListUtils;
import com.muso.utils.thread.ThreadHandler;

public abstract class InfringementSummaryPageBase extends AbstractBasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfringementSummaryPageBase.class);

    // MENU BUTTONS
    @FindBy(css = ReportMenuButton_CSS)
    protected WebElement ReportMenuButton;

    @FindBy(css = MembersMenuButton_CSS)
    protected WebElement MembersMenuButton;

    @FindBy(css = DateRangeMenuButton_CSS)
    protected WebElement DateRangeMenuButton;

    @FindBy(css = CampaignMenuButton_CSS)
    protected WebElement CampaignMenuButton;

    @FindBy(css = TypeMenuButton_CSS)
    protected WebElement TypeMenuButton;

    @FindBy(css = ProductMenuButton_CSS)
    protected WebElement ProductMenuButton;

    @FindBy(css = SupportButton_CSS)
    protected WebElement SupportButton;

    @FindBy(css = AccountButton_CSS)
    protected WebElement AccountButton;

    @FindBy(css = LogoutButton_CSS)
    protected WebElement LogoutButton;

    // MENU OPTIONS HOLDERS
    @FindBy(css = reportHolder_CSS)
    protected WebElement reportOptionsHolder;

    @FindBy(css = memberHolder_CSS)
    protected WebElement membersOptionsHolder;

    @FindBy(css = dateRangeOptionsHolder_CSS)
    protected WebElement dateRangeOptionsHolder;

    @FindBy(css = campaignOptionsHolder_CSS)
    protected WebElement campaignOptionsHolder;

    @FindBy(css = campaignSearchBox_CSS)
    protected WebElement campaignSearchBox;

    @FindBy(css = typeHolder_CSS)
    protected WebElement typeOptionsHolder;

    @FindBy(css = typeSearchBox_CSS)
    protected WebElement typeSearchBox;

    @FindBy(css = productOptionsHolder_CSS)
    protected WebElement productOptionsHolder;

    @FindBy(css = productSearchBox_CSS)
    protected WebElement productSearchBox;

    public InfringementSummaryPageBase(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

    }

    public boolean isMenuEnabled(MenuType menu) {
        boolean isMenuEnabled = false;

        switch (menu) {
        case REPORT:
            if (!ReportMenuButton.getAttribute("aria-disabled").equals("true"))
                isMenuEnabled = true;
            break;
        case DATE_RANGE:
            if (!DateRangeMenuButton.getAttribute("aria-disabled").equals("true"))
                isMenuEnabled = true;
            break;
        case CAMPAIGN:
            if (!CampaignMenuButton.getAttribute("aria-disabled").equals("true"))
                isMenuEnabled = true;
            break;
        case PRODUCT:
            if (!ProductMenuButton.getAttribute("aria-disabled").equals("true"))
                isMenuEnabled = true;
            break;
        case TYPE:
            if (!TypeMenuButton.getAttribute("aria-disabled").equals("true"))
                isMenuEnabled = true;
            break;
        default:
            throw new InvalidArgumentException("Unknown report " + menu.getName());
        }
        return isMenuEnabled;
    }

    public boolean isMenuDisplayed(MenuType menu) {
        boolean isMenuDisplayed = true;

        switch (menu) {
        case MEMBERS:
            try {
                driver.findElement(By.cssSelector("button[title='Members']"));
            } catch (NoSuchElementException ex) {
                return false;
            }

            break;
        default:
            throw new InvalidArgumentException("Unknown report " + menu.getName());
        }

        return isMenuDisplayed;
    }

    public boolean isMenuExpanded(MenuType menu) {
        switch (menu) {
        case REPORT:
            return isMenuExpanded(reportOptionsHolder);
        case DATE_RANGE:
            return isMenuExpanded(dateRangeOptionsHolder);
        case CAMPAIGN:
            return isMenuExpanded(campaignOptionsHolder);
        case PRODUCT:
            return isMenuExpanded(productOptionsHolder);
        case TYPE:
            return isMenuExpanded(typeOptionsHolder);
        case MEMBERS:
            return isMenuExpanded(membersOptionsHolder);
        default:
            throw new InvalidArgumentException("Unknown report " + menu.getName());
        }
    }

    public void expandSideBar() {
        if (!isSidebarExpanded())
            clickOnToggleButton();
    }

    public boolean isSidebarExpanded() {

        if (ToggleButton.isDisplayed()) {
            if (SidebarMenu.getAttribute("class").contains("show"))
                return true;
            return false;
        } else {
            return true;
        }
    }

    public void clickOnToggleButton() {
        LOGGER.debug("Expanding SideMenu.");
        ToggleButton.click();
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
        LOGGER.debug("Collapsing all menus");
        if (isMenuExpanded(reportOptionsHolder)) {
            clickOnReportMenuButton();
            return;
        }
        if (isMenuExpanded(dateRangeOptionsHolder)) {
            clickOnDateRangeMenuButton();
            return;
        }
        if (isMenuExpanded(campaignOptionsHolder)) {
            clickOnCampaignMenuButton();
            return;
        }
        if (isMenuExpanded(productOptionsHolder)) {
            clickOnProductMenuButton();
            return;
        }
        if (isMenuExpanded(typeOptionsHolder)) {
            clickOnTypeMenuButton();
            return;
        }
    }

    public void setMember(String optionName) {
        LOGGER.debug("Selecting {} member", optionName);
        WebElement memberElement = WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfMemberInFilter(optionName));
        Clicker.safeClick(driver, memberElement);

        persistenceManager.setMember(optionName);
    }

    public void setReport(String optionName) {
        LOGGER.debug("Selecting {} report", optionName);
        WebElement reportElement = WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfReportInFilter(optionName));
        Clicker.safeClick(driver, reportElement);

        persistenceManager.setReport(ReportType.fromString(optionName));
    }

    public void setCampaign(String optionName) {
        LOGGER.debug("Selecting {} campaign", optionName);

        final WebElement campaignElement = WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfCampaignInFilter(optionName));
        Clicker.safeClick(driver, campaignElement);

        //todo add campaign to persistenceManager
    }

    public void clearCampaignSelections() {
        for (String campaign : getCampaignFilterSelectedOptionsFromHolder()) {
            removeCampaign(campaign);
        }
    }

    public void removeCampaign(String optionName) {
        LOGGER.debug("Removing {} campaign", optionName);
        boolean itemRemoved = false;

        List<WebElement> campaignSelectionHolder = driver.findElements(By.cssSelector(campaignItemHolder_CSS));
        List<WebElement> categorySelectionHolder = driver.findElements(By.cssSelector(campaignCategoryHolder_CSS));

        if (campaignSelectionHolder.isEmpty() && categorySelectionHolder.isEmpty()) {
            LOGGER.warn("Unable to remove campaing {}. There is no campaign selected", optionName);
        } else {
            for (WebElement selectedItem : campaignSelectionHolder) {
                if (selectedItem.getText().equals(optionName)) {
                    selectedItem.findElement(By.cssSelector(".glyphicon.glyphicon-remove-sign")).click();
                    itemRemoved = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.stalenessOf(selectedItem));
                    break;
                }
            }

            if (!itemRemoved)
                for (WebElement selectedItem : categorySelectionHolder) {
                    if (selectedItem.getText().equals(optionName)) {
                        selectedItem.findElement(By.className("glyphicon glyphicon-remove-sign")).click();
                        persistenceManager.removeCampaign(selectedItem.getText());
                        itemRemoved = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.stalenessOf(selectedItem));
                        break;
                    }
                }

            if (itemRemoved) {
                LOGGER.debug("{} campaign removed", optionName);
            } else {
                LOGGER.debug("{} campaign already removed", optionName);
            }

        }

    }

    public void setDateRange(String optionName) {
        LOGGER.debug("Selecting {} Date Range", optionName);
        WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfDateRangeInFilter(optionName)).click();

        persistenceManager.setDateRange(DateRangeType.fromString(optionName));
    }

    public void setProduct(String optionName) {
        LOGGER.debug("Selecting '{}' Product", optionName);

        final WebElement productElement = WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfProductInFilter(optionName));
        Clicker.safeClick(driver, productElement);

        persistenceManager.addProduct(optionName);
    }

    public void clearProductSelections() {
        for (String product : getProductSelection())
            removeProduct(product);

    }

    public void removeProduct(String optionName) {
        LOGGER.debug("Removing '{}' product", optionName);
        boolean itemRemoved = false;

        List<WebElement> productSelectionHolder = driver.findElements(By.cssSelector("muso-product-filter muso-filter-product-item"));

        if (productSelectionHolder.isEmpty()) {
            LOGGER.warn("Unable to remove product {}. There is no product selected", optionName);
        } else {
            for (WebElement selectedItem : productSelectionHolder) {
                if (selectedItem.getText().equals(optionName)) {
                    selectedItem.findElement(By.cssSelector(".glyphicon.glyphicon-remove-sign")).click();
                    persistenceManager.removeProduct(optionName);
                    itemRemoved = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.stalenessOf(selectedItem));
                    break;
                }
            }

            if (itemRemoved) {
                LOGGER.debug("{} product removed", optionName);
            } else {
                LOGGER.debug("{} product already removed", optionName);
            }

        }

    }

    public void setType(String optionName) {
        LOGGER.debug("Selecting '{}' type", optionName);

        final WebElement typeElement = WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExtExpectedConditions.visibilityOfTypeInFilter(optionName));
        Clicker.safeClick(driver, typeElement);

        persistenceManager.setType(optionName);
    }

    public void clearTypeSelections() {
        // TODO Auto-generated method stub

    }

    public void removeType(String optionName) {
        LOGGER.debug("Removing '{}' type", optionName);
        boolean itemRemoved = false;

        List<WebElement> productSelectionHolder = driver.findElements(By.cssSelector("muso-filter-type muso-filter-type-item"));

        if (productSelectionHolder.isEmpty()) {
            LOGGER.warn("Unable to remove product {}. There is no product selected", optionName);
        } else {
            for (WebElement selectedItem : productSelectionHolder) {
                if (selectedItem.getText().equals(optionName)) {
                    selectedItem.findElement(By.cssSelector(".glyphicon.glyphicon-remove-sign")).click();
                    persistenceManager.removeProduct(optionName);
                    itemRemoved = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.stalenessOf(selectedItem));
                    break;
                }
            }

            if (itemRemoved) {
                LOGGER.debug("{} product removed", optionName);
            } else {
                LOGGER.debug("{} product already removed", optionName);
            }

        }
    }

    protected boolean isMenuExpanded(WebElement menuElement) {
        try {
            if (Boolean.valueOf(menuElement.getAttribute("aria-expanded")) || Boolean.valueOf(menuElement.getAttribute("style").contains("display: block"))) {
                return true;
            }

        } catch (NoSuchElementException ex) {
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

    public int getCampaignSelectionNumber() {

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
                if (!selectedItem.findElement(By.cssSelector("a")).getAttribute("class").equals("dropdown-header")) {
                    selectedCampaigns.add(selectedItem.getText());
                }
            }
        }

        LOGGER.info("Found {} campaigns selected: {}", selectedCampaigns.size(), selectedCampaigns.toString());

        return selectedCampaigns.size();
    }

    public ArrayList<String> getCampaignFilterSelectedOptionsFromFilter() {

        ArrayList<String> selectedCampaigns = new ArrayList<String>();
        List<WebElement> campaignElements = getCampaignFilterWebElements();

        for (WebElement campaignElement : campaignElements) {
            if (campaignElement.getAttribute("class").equals("selected")) {
                selectedCampaigns.add(campaignElement.getText());
            }
        }

        if (selectedCampaigns.isEmpty()) {
            selectedCampaigns.add("All campaigns");
        }

        LOGGER.info("Found {} campaigns selected: {}", selectedCampaigns.size(), selectedCampaigns.toString());

        return selectedCampaigns;

    }

    public ArrayList<String> getCampaignOptions() {
        if (!isMenuExpanded(MenuType.CAMPAIGN)) {
            collapseAllMenus();
            clickOnCampaignMenuButton();
        }

        ArrayList<String> campaigns = new ArrayList<String>();
        List<WebElement> campaignElements = getCampaignFilterWebElements();

        for (WebElement campaignElement : campaignElements) {
            if (!campaignElement.getAttribute("class").equals("no-results") && !campaignElement.getText().isEmpty())
                campaigns.add(campaignElement.getText());
        }

        LOGGER.debug("Found {} campaigns in Campaign Filter", campaigns.size());

        return campaigns;
    }

    private ArrayList<WebElement> getCampaignFilterWebElements() {

        ArrayList<WebElement> optionsList = new ArrayList<WebElement>();

        List<WebElement> campaignElements = campaignOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement element : campaignElements) {
            if (!element.getAttribute("class").equals("hidden"))
                optionsList.add(element);
        }
        return optionsList;
    }

    public ArrayList<String> getCampaignFilterSelectedOptionsFromHolder() {

        if (isMenuExpanded(MenuType.CAMPAIGN)) {
            clickOnCampaignMenuButton();
        }

        ArrayList<String> selectedCampaigns = new ArrayList<String>();
        List<WebElement> campaignSelectionHolder = driver.findElements(By.cssSelector("muso-campaign-filter muso-filter-campaign-item"));
        List<WebElement> categorySelectionHolder = driver.findElements(By.cssSelector("muso-campaign-filter muso-filter-campaign-type-item"));

        if (campaignSelectionHolder.isEmpty() && categorySelectionHolder.isEmpty()) {
            selectedCampaigns.add("All campaigns");
        } else {
            for (WebElement selectedItem : campaignSelectionHolder) {
                selectedCampaigns.add(selectedItem.getText());
            }
            for (WebElement selectedItem : categorySelectionHolder) {
                selectedCampaigns.add(selectedItem.getText());
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

        WebElement menuSelection = dateRangeOptionsHolder.findElement(By.cssSelector("li.active"));
        LOGGER.debug("Date Range filter has the following options selected: {}", menuSelection.getText());

        return menuSelection.getText();
    }

    public ArrayList<String> getProductSelection() {

        ArrayList<String> selectedProducts = new ArrayList<String>();
        List<WebElement> products = getProductFilterWebElements();

        for (WebElement productElement : products) {
            if (productElement.getAttribute("class").equals("selected")) {
                selectedProducts.add(productElement.getText());
            }
        }

        if (selectedProducts.isEmpty()) {
            selectedProducts.add("All products");
        }

        LOGGER.debug("Product filter has the following options selected: {}", selectedProducts.toString());

        return selectedProducts;
    }

    public ArrayList<String> getProductOptions() {

        ArrayList<String> products = new ArrayList<String>();
        List<WebElement> productElements = getProductFilterWebElements();

        for (WebElement productElement : productElements) {
            if (!productElement.getAttribute("class").equals("no-results") && !productElement.getText().isEmpty())
                products.add(productElement.getText());
        }

        return products;
    }

    private List<WebElement> getProductFilterWebElements() {

        ArrayList<WebElement> optionsList = new ArrayList<WebElement>();

        List<WebElement> productElements = (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(productOptionsItems_CSS), 0));

        for (WebElement element : productElements) {
            if (!element.getAttribute("class").equals("hidden"))
                optionsList.add(element);
        }
        return optionsList;

    }

    public ArrayList<String> getProductFilterSelectedOptionsFromHolder() {

        ArrayList<String> selectedProducts = new ArrayList<String>();
        List<WebElement> productSelectionHolder = driver.findElements(By.cssSelector(productOptionsSelectionHolder_CSS));

        if (productSelectionHolder.isEmpty()) {
            selectedProducts.add("All products");
        } else {
            for (WebElement selectedItem : productSelectionHolder) {
                selectedProducts.add(selectedItem.getText());
            }
        }

        LOGGER.info("Found {} products displayed in Product Holder: {}", selectedProducts.size(), selectedProducts.toString());

        return selectedProducts;
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

    public ArrayList<String> getTypeOptions() {
        ArrayList<String> types = new ArrayList<String>();
        List<WebElement> menuOptions = getTypeFilterWebElements();

        for (WebElement option : menuOptions) {
            if (!option.getAttribute("class").equals("no-results") && !option.getText().isEmpty())
                types.add(option.getText());
        }

        return types;

    }

    public ArrayList<String> getTypeFilterSelectedOptionsFromHolder() {
        if (isMenuExpanded(MenuType.TYPE)) {
            clickOnTypeMenuButton();
        }

        ArrayList<String> selectedTypes = new ArrayList<String>();
        List<WebElement> typesSelectionHolder = driver.findElements(By.cssSelector(productOptionsSelectionHolder_CSS));

        if (typesSelectionHolder.isEmpty()) {
            selectedTypes.add("All Types");
        } else {
            for (WebElement selectedItem : typesSelectionHolder) {
                if (selectedItem.getAttribute("style").equals("display: block") || selectedItem.getAttribute("style").isEmpty())
                    selectedTypes.add(selectedItem.getText());
            }

        }

        LOGGER.info("Found {} types selected: {}", selectedTypes.size(), selectedTypes.toString());

        return selectedTypes;
    }

    public void searchForTypeAndSelect(String optionName) {
        try {
            searchForType(optionName);

            List<String> searchResults = getTypeOptions();

            for (String result : searchResults) {
                ThreadHandler.shortSleep();
                setType(result);
            }
        } catch (InvalidElementStateException ex) {
            LOGGER.warn("Search BOX not available. Selecting {} type from list", optionName);
            setType(optionName);
        }
    }

    public void searchForType(String optionName) throws InvalidElementStateException {
        LOGGER.debug("Searching for {} option under Type", optionName);
        typeSearchBox.clear();
        typeSearchBox.sendKeys(optionName);
        ThreadHandler.shortSleep();
    }

    public void searchForProductAndSelect(String optionName) {
        try {
            searchForProduct(optionName);

            List<String> searchResults = getProductOptions();

            for (String result : searchResults) {
                ThreadHandler.shortSleep();
                setProduct(result);
            }
        } catch (InvalidElementStateException ex) {
            LOGGER.warn("Search BOX not available. Selecting {} product from list", optionName);
            setProduct(optionName);
        }
    }

    public void searchForProduct(String optionName) throws InvalidElementStateException {
        LOGGER.debug("Searching for {} option under Product", optionName);
        productSearchBox.clear();
        productSearchBox.sendKeys(optionName);
        ThreadHandler.shortSleep();
    }

    public void searchForCampaignAndSelect(String optionName) {
        try {
            searchForCampaign(optionName);

            List<String> searchResults = getCampaignOptions();

            for (String result : searchResults) {
                ThreadHandler.shortSleep();
                setCampaign(result);
            }
        } catch (InvalidElementStateException ex) {
            LOGGER.warn("Search BOX not available. Selecting {} campaign from list", optionName);
            setCampaign(optionName);
        }

    }

    public void searchForCampaign(String optionName) throws InvalidElementStateException {
        LOGGER.debug("Searching for {} option under Campaign", optionName);

        campaignSearchBox.clear();
        campaignSearchBox.sendKeys(optionName);
        ThreadHandler.shortSleep();

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

    public ArrayList<WebElement> getMembersOptions() {

        LOGGER.debug("Retrieving Members filter availabe options");
        ArrayList<WebElement> optionsList = new ArrayList<WebElement>();

        List<WebElement> membersElements = membersOptionsHolder.findElements(By.cssSelector("li"));
        for (WebElement element : membersElements) {
            optionsList.add(element);
        }

        LOGGER.debug(optionsList.toString());
        return optionsList;
    }

    @Override
    public void checkUIElements() {
        super.checkUIElements();

        // Verify Report filter selection
        assertEquals(persistenceManager.getReport().getText() + " is not the default selection for Report.", persistenceManager.getReport().getText(), getReportSelection());

        // Verify Date Range filter selection
        assertEquals(persistenceManager.getDateRange() + " is not the default selection for DateRange.", persistenceManager.getDateRange(), getDateRangeSelection());

        // Verify Type filter selection
        assertTrue(persistenceManager.getType() + " is not the default selection for Type.", ListUtils.conpareArrays(getTypeSelection(), persistenceManager.getType()));

        // TODO Add Product filter validation

        // Verify Campaign filter selection and Header
        verifyCampaignFilterAndHeader();

        // Verify that selected Report is displayed in page Header
        assertEquals(persistenceManager.getReport().getText() + " is not displayed in page Header.", persistenceManager.getReport().getText(), getReportfromHeader());

        // Verify that selected Date Range is displayed in page Header
        assertEquals(persistenceManager.getDateRange() + " is not displayed in page Header.", persistenceManager.getDateRange(), getDateRangeFromHeader());

    }

    public boolean isElementDisplayed(WebElement element) {
        if (element.getAttribute("style").contains("opacity: 1"))
            return true;
        return false;
    }

    public boolean isElementSelected(WebElement element) {
        if (element.getAttribute("class").contains("selected"))
            return true;
        return false;
    }

    public boolean isCampaignDisplayedInHeader(String campaignName) {
        return getCampaignFromHeader().contains(campaignName);
    }

    // PRIVATE METHODS
    public void clickOnTypeMenuButton() {
        LOGGER.debug("Clicking on Type menu button");
        Clicker.safeClick(driver, TypeMenuButton);
        // TypeMenuButton.click();
    }

    public void clickOnDateRangeMenuButton() {
        LOGGER.debug("Clicking on Date Range menu button");
        Clicker.safeClick(driver, DateRangeMenuButton);
        //DateRangeMenuButton.click();

    }

    public void clickOnCampaignMenuButton() {
        LOGGER.debug("Clicking on Campaign menu button");
        Clicker.safeClick(driver, CampaignMenuButton);
        // CampaignMenuButton.click();
    }

    public void clickOnReportMenuButton() {
        LOGGER.debug("Clicking on Report menu button");
        Clicker.safeClick(driver, ReportMenuButton);
        //ReportMenuButton.click();
    }

    public void clickOnProductMenuButton() {
        LOGGER.debug("Clicking on Product menu button");
        Clicker.safeClick(driver, ProductMenuButton);
        // ProductMenuButton.click();
    }

    public void clickOnMembersMenuButton() {
        LOGGER.debug("Clicking on Members menu button");
        Clicker.safeClick(driver, MembersMenuButton);
        // MembersMenuButton.click();
    }

    private void verifyCampaignFilterAndHeader() {
        if (!isMenuExpanded(MenuType.CAMPAIGN)) {
            collapseAllMenus();
            clickOnCampaignMenuButton();
        }
        ArrayList<String> selectedCampaigns = getCampaignFilterSelectedOptionsFromFilter();
        final int expected = persistenceManager.getCampaigns().size();
        final int actual = selectedCampaigns.size();

        assertTrue("The number of selected campaign is incorrect.\n Expected " + persistenceManager.getCampaigns().toString() + " but found: " + selectedCampaigns.toString(), expected == actual);

        if (getCampaignSelectionNumber() > 6) {
            assertEquals("'Multiple campaigns' is not displayed in header", "Multiple campaigns", getCampaignFromHeader());
        } else {
            if (getCampaignSelectionNumber() > 0) {
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

    private List<WebElement> getTypeFilterWebElements() {
        ArrayList<WebElement> optionsList = new ArrayList<WebElement>();
        List<WebElement> menuOptions = typeOptionsHolder.findElements(By.cssSelector("li"));

        for (WebElement element : menuOptions) {
            if (!element.getAttribute("class").equals("hidden"))
                optionsList.add(element);
        }

        return optionsList;
    }
}
