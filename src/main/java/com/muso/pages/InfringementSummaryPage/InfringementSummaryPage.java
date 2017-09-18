package com.muso.pages.InfringementSummaryPage;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InfringementSummaryPage extends InfringementSummaryPageBase {

    @FindBy(id = "counterAllTime")
    protected WebElement totalRemovals;

    @FindBy(id = "counterLastWeek")
    protected WebElement lastWeekRemoval;

    @FindBy(id = "counterCustomPeriod")
    protected WebElement customRemoval;

    public InfringementSummaryPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }

    public int getTotalRemovals() {
        return Integer.valueOf(totalRemovals.getText());
    }

    public int getLastWeekRemovals() {
        return Integer.valueOf(lastWeekRemoval.getText());
    }

    public int getCustomRemovals() {
        return Integer.valueOf(customRemoval.getText());
    }

    public void checkUIElements() {
        throw new InvalidArgumentException("NOT IMPLEMENTED");
    }

}
