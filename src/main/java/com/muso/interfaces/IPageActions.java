package com.muso.interfaces;

import com.muso.pages.AbstractPage;

public interface IPageActions {

    public boolean isOptionSelected(String optionName);

    public AbstractPage expandReportMenu();

    public AbstractPage expandCampaignMenu();

    public AbstractPage expandDateRangeMenu();

    public AbstractPage expandTypeMenu();

    public AbstractPage expandProductMenu();

    void checkUIElements();

    void navigateTo(String applicationURL);

}
