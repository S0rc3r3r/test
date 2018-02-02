package com.muso.testers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.dataproviders.UserDataProvider;
import com.muso.models.User;
import com.muso.pages.AntiPiracyLinksPage.AntiPiracyLinksPage;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPage;
import com.muso.pages.MarketAnalyticsPage.MarketAnalyticsPage;
import com.muso.pages.SubmitInfringementsPage.SubmitInfringementsPage;
import com.muso.selenium.base.waits.WebDriverWaitManager;

public class BaseTester {
    private final Logger LOGGER = LoggerFactory.getLogger(BaseTester.class);
    protected static InfringementSummaryPage infringementSummaryPage;
    protected static AntiPiracyLinksPage antiPiracyLinksPage;
    protected static MarketAnalyticsPage marketAnalyticsPage;
    protected static SubmitInfringementsPage submitInfringementsPage;
    protected static WebDriver driver;

    public BaseTester(WebDriver driver) {
        BaseTester.driver = driver;
    }

    public void navigateToDashboard(String userFile) {

        final String application_url = getApplicationURL(userFile);

        LOGGER.info("Navigate to: " + application_url);
        driver.get(application_url);

        WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.titleIs("MUSO Dashboard"));
        infringementSummaryPage = new InfringementSummaryPage(driver);
    }

    public void openDashboard(String userFile) {

        final String application_url = getApplicationURL(userFile);

        LOGGER.info("Navigate to: " + application_url);
        driver.get(application_url);
    }

    private String getApplicationURL(String userFile) {
        final UserDataProvider userDataProvider = new UserDataProvider("/testdata/json/" + userFile + ".json");

        User user = userDataProvider.getData();

        return System.getProperty("application_url") + "/?token=" + user.getJwt_token();

    }
}
