package com.muso.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class FindElementLocator implements Locator {

    private final SearchContext searchContext;
    private final By byLoc;

    public FindElementLocator(final SearchContext searchContext, final By byLoc) {
        this.searchContext = searchContext;
        this.byLoc = byLoc;
    }

    @Override
    public WebElement locate() {
        return searchContext.findElement(byLoc);
    }
}
