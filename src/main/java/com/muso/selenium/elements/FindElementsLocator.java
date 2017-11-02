package com.muso.selenium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class FindElementsLocator implements Locator {
    private final SearchContext searchContext;
    private final By byLoc;
    private final int index;

    public FindElementsLocator(final SearchContext searchContext, final By byLoc, final int index) {
        this.searchContext = searchContext;
        this.byLoc = byLoc;
        this.index = index;
    }

    @Override
    public WebElement locate() {
        return searchContext.findElements(byLoc).get(index);
    }
}
