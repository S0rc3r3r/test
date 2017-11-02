package com.muso.selenium.base.waits.conditions;

public class Locator {

    private final String value;
    private final LocatorTypes type;

    public Locator(final String value, final LocatorTypes type) {
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return value;
    }

    public String getType() {
        return type.getLocatorString();
    }
}
