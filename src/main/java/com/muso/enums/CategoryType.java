package com.muso.enums;

import java.util.ArrayList;

import org.openqa.selenium.InvalidArgumentException;

public enum CategoryType {
    PRIVATE_TORRENT("Private Torrent", "Dwonload"),
    PUBLIC_TORRENT("Public Torrent", "Dwonload"),
    STREAM_RIPPER("Stream Ripper", "Dwonload"),
    WEB_DOWNLOAD("Web Download", "Download"),
    WEB_STREAMING("Web Streaming", "Streaming");

    private String name;
    private String group;

    private CategoryType(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public String getGroup() {
        return this.group;
    }

    public static CategoryType fromString(String text) {
        if (text != null) {
            for (CategoryType type : CategoryType.values()) {
                if (text.equalsIgnoreCase(type.getName())) {
                    return type;
                }
            }
        }
        throw new InvalidArgumentException("Unknown Category: " + text);
    }

    public static ArrayList<String> getAvailableOptions() {
        ArrayList<String> availableOptions = new ArrayList<String>();

        for (CategoryType type : CategoryType.values()) {
            availableOptions.add(type.getName());
        }

        return availableOptions;
    }

}
