package com.muso.pages.SubmitInfringementsPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.muso.pages.InfringementSummaryPage.InfringementSummaryPageBase;

public class SubmitInfringementsPage extends InfringementSummaryPageBase {

    @FindBy(css = "muso-submission-history-text-area > div.row")
    protected WebElement submitInfringementsHolder;

    @FindBy(id = "submitInfringementButton")
    protected WebElement submitInfringementsButton;

    @FindBy(css = "muso-submission-history div.row")
    protected WebElement submitInfringementsHistoryHolder;

    public SubmitInfringementsPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(submitInfringementsButton));

    }

    @Override
    public void checkUIElements() {
        super.checkUIElements();

        assertTrue("Submit Infringements button should be dispayed", isSubmitInfringementsButtonDisplayed());
        assertTrue("Submit Infringements History should be dispayed", isElementDisplayed(submitInfringementsHistoryHolder));
        assertTrue("Submit Infringements should be dispayed", isElementDisplayed(submitInfringementsHolder));
        if (getCampaignSelectionNumber() == 1) {
            if (!persistenceManager.getCampaigns().get(0).equals("All campaigns")) {
                assertTrue("Submit Infringements button should be enabled", isSubmitInfringementsButtonEnabled());
            }
        } else {
            assertFalse("Submit Infringements button should be disabled", isSubmitInfringementsButtonEnabled());
        }

    }

    private boolean isSubmitInfringementsButtonDisplayed() {
        return submitInfringementsButton.isDisplayed();
    }

    private boolean isSubmitInfringementsButtonEnabled() {
        return !Boolean.valueOf(submitInfringementsButton.getAttribute("aria-disabled"));
    }

}
