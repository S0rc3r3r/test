package com.muso.enums;

import java.util.ArrayList;

import org.openqa.selenium.InvalidArgumentException;

public enum RegionType {
    ARGENTINA("Argentina", ""),
    BRAZIL("Brazil", ""),
    CANADA("Canada", ""),
    MOLDOVA("Moldova", ""),
    ROMANIA("Romania", ""),
    SPAIN("Spain", ""),
    UK("United Kindom", ""),
    US("United States of America", "");

    private String name;
    private String group;

    private RegionType(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public String getGroup() {
        return this.group;
    }

    public static RegionType fromString(String text) {
        if (text != null) {
            for (RegionType type : RegionType.values()) {
                if (text.equalsIgnoreCase(type.getName())) {
                    return type;
                }
            }
        }
        throw new InvalidArgumentException("Unknown Region Type: " + text);
    }

    public static ArrayList<String> getAvailableOptions() {
        ArrayList<String> availableOptions = new ArrayList<String>();

        for (RegionType type : RegionType.values()) {
            availableOptions.add(type.getName());
        }

        return availableOptions;
    }
}
