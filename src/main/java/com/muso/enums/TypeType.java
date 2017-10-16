package com.muso.enums;

import java.util.ArrayList;

import org.openqa.selenium.InvalidArgumentException;

public enum TypeType {

    ALL("All types", "ALL"),
    FILES("Files", "Files"),
    TORRENT("Torrent", "Files"),
    SOCIAL_MEDIA_SITE("Social Media Site", "Files"),
    INDEXER_OR_SEARCH_SITE("Indexer or Search Site", "Files"),
    BLOG_OR_FORUM("Blog or Forum", "Files"),
    CYBERLOCKER("Cyberlocker", "Files"),
    ECOMERCE("eComerce", "Files"),
    FORWARDER("Forwarder", "Files"),
    STREAMING("Streaming", "Files"),
    UGC("UGC", "Files"),
    SEARCH_ENGINE("Search engine", "SearchEngine"),
    GOOGLE("Google", "SearchEngine"),
    SEARCH_RESULT("Search result", "SearchResult"),
    SEARCH_ENGINE_DELISTINGS("Search Engine Delistings", null),
    MP3("Mp3", "SearchResult");

    private String text, group;

    private TypeType(String text, String group) {
        this.text = text;
        this.group = group;
    }

    public String getText() {
        return this.text;
    }

    public String getGroup() {
        return this.group;
    }

    public static boolean isTypeValid(String text) {
        for (TypeType type : TypeType.values()) {
            if (text.equalsIgnoreCase(type.getText())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getAvailableOptions() {
        ArrayList<String> availableOptions = new ArrayList<String>();

        for (TypeType type : TypeType.values()) {
            availableOptions.add(type.getText());
        }

        return availableOptions;
    }

    public static TypeType fromString(String text) {
        if (text != null) {
            for (TypeType type : TypeType.values()) {
                if (text.equalsIgnoreCase(type.getText())) {
                    return type;
                }
            }
        }
        throw new InvalidArgumentException("Unknown Type: " + text);
    }

}
