package com.muso.enums;

import java.util.ArrayList;

public enum ReportType {

    Infringement_Summary("Infringement Summary"),
    Anti_Piracy_Links("Anti Piracy Links"),
    Submit_Infringements("Submit Infringements"),
    Market_Analytics("Market Analytics");

    public static ReportType get(int id) {
        for (ReportType type : values()) {
            if (type.ordinal() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    private String text;

    public String getText() {
        return this.text;
    }

    private ReportType(String text) {
        this.text = text;
    }

    public static ReportType fromString(String text) {
        if (text != null) {
            for (ReportType type : ReportType.values()) {
                if (text.equalsIgnoreCase(type.getText())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static boolean isReportTypeValid(String text) {
        for (ReportType type : ReportType.values()) {
            if (text.equalsIgnoreCase(type.getText())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getAvailableOptions() {
        ArrayList<String> availableOptions = new ArrayList<String>();

        for (ReportType type : ReportType.values()) {
            availableOptions.add(type.getText());
        }

        return availableOptions;
    }

}
