package com.muso.stepDefinition;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.muso.testers.CampaignTester;
import com.muso.testers.DashboardTester;
import com.muso.testers.DateRangeTester;
import com.muso.testers.MemberTester;
import com.muso.testers.ProductTester;
import com.muso.testers.ReportTester;
import com.muso.testers.TypeTester;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BaseStepDefinition {

    protected String expandedMenu;
    protected static DashboardTester dashboardTester;
    protected static CampaignTester campaignTester;
    protected static ProductTester productTester;
    protected static TypeTester typeTester;
    protected static ReportTester reportTester;
    protected static MemberTester memberTester;
    protected static DateRangeTester dateRangeTester;
    protected WebDriver driver;

    public BaseStepDefinition() {
        driver = Hooks.driver;
        dashboardTester = new DashboardTester(driver);

    }

    @Given("^I'm located on new dashboard page as '(.*)' user$")
    public void I_am_located_on_dashboard_page(String user) throws Throwable {

        dashboardTester.navigateToDashboard(user);

        assertEquals("MUSO Dashboard", dashboardTester.getPageTitle());

        initTesters();
    }

    @Given("^I'm accessing dashboard using '(.*)'$")
    public void I_am_accessing_dashboard(String user) throws Throwable {
        dashboardTester.openDashboard(user);
    }

    @Then("^I'm redirected to musoWeb Login Page$")
    public void I_am_redirected_to_login_page() {
        assertEquals("Login – MUSO – DISCOVER, PROTECT, CONNECT", dashboardTester.getPageTitle());
    }

    private void initTesters() {
        campaignTester = new CampaignTester(driver);
        productTester = new ProductTester(driver);
        typeTester = new TypeTester(driver);
        reportTester = new ReportTester(driver);
        memberTester = new MemberTester(driver);
        dateRangeTester = new DateRangeTester(driver);
    }

}
