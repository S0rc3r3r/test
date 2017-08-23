package step_definition;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.DateRangeType;
import com.muso.enums.ReportType;
import com.muso.enums.UserType;
import com.muso.pages.DashboardPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashBoard {

    protected WebDriver driver = Hooks.driver;
    private String activePage;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashBoard.class);
    private DashboardPage dashboardPage;

    @Given("^I'm located on dashboard page as '(NORMAL|ADMIN)' user$")
    public void I_am_located_on_dashboard_page(String userRole) throws Throwable {

        dashboardPage = new DashboardPage(driver, UserType.valueOf(userRole));
        assertEquals("MUSO Dashboard", dashboardPage.getPageTitle());

    }

    @When("^I click on '(Report|Date Range|Campaign|Type)' button$")
    public void I_click_on_button(String buttonName) {
        this.activePage = buttonName;

        switch (buttonName) {
        case "Report":
            dashboardPage.clickOnReportButton();
            break;
        case "Date Range":
            dashboardPage.clickOnDateRangeButton();
            break;
        case "Campaign":
            dashboardPage.clickOnCampaignButton();
            break;
        case "Type":
            dashboardPage.clickOnTypeButton();
            break;
        default:
            throw new InvalidArgumentException("Unknown button label:" + buttonName);
        }
    }

    @Then("^I should see (\\d+) available options$")
    public void I_should_see_available_options(int i) {

        dashboardPage.getAvailableOptions(activePage);

        switch (activePage) {
        case "Report":
            assertEquals(activePage + " should have 4 available options.", i, 4);
            break;
        case "Date Range":
            assertEquals(activePage + " should have 8 available options.", i, 8);
            break;
        }

    }

    @And("^'(.+)' option is there(| selected)?$")
    public void option_is_there(String option, String isDefault) {
        switch (activePage) {
        case "Report":
            assertEquals(option + " is not a valid Report option.", true, ReportType.isReportTypeValid(option));
            assertEquals(option + " is not displayed on UI", true, dashboardPage.isOptionDisplayed(option, activePage));
            break;
        case "Date Range":
            assertEquals(option + " is not a valid Date Range option.", true, DateRangeType.isDateRangeTypeValid(option));
            assertEquals(option + " is not displayed on UI", true, dashboardPage.isOptionDisplayed(option, activePage));
            break;
        default:
            break;
        }

        if (!isDefault.isEmpty())
            assertEquals(option + " is not selected.", true, dashboardPage.isOptionSelected(option, activePage));

    }

}
