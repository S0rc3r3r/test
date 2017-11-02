package com.muso.selenium.base.waits.conditions;

public enum LocatorTypes {

    ID("id"),
    NAME("name"),
    XPATH("xPath"),
    CSS("CSS"),
    CLASSNAME("className");

    private LocatorTypes(final String locatorString) {
        this.locatorString = locatorString;
    }

    private String locatorString;

    public String getLocatorString() {
        return locatorString;
    }
};
