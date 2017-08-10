package com.muso.enums;

public enum DateRangeType {

    ALL_TIME("All Time"), LAST_12_MONTHS("Last 12 Months"), LAST_6_MONTHS("Last 6 Months"), LAST_2_MONTHS(
            "Last 2 Months"), LAST_MONTH(
                    "Last Month"), LAST_4_WEEKS("Last 4 Weeks"), LAST_WEEK("Last Week"), CUSTOM_RANGE("Custom Range");

    public static DateRangeType get(int id) {
        for (DateRangeType type : values()) {
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

    private DateRangeType(String text) {
        this.text = text;
    }

    public static DateRangeType fromString(String text) {
        if (text != null) {
            for (DateRangeType type : DateRangeType.values()) {
                if (text.equalsIgnoreCase(type.getText())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static boolean isDateRangeTypeValid(String text) {
        for (DateRangeType type : DateRangeType.values()) {
            if (text.equalsIgnoreCase(type.getText())) {
                return true;
            }
        }
        return false;
    }

}
