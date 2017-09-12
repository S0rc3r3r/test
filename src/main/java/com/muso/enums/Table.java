package com.muso.enums;

import java.util.Arrays;
import java.util.List;

public enum Table {
    REMOVAL_DETAILS("Removal Details", COLUMNS.REMOVAL_DETAILS);

    private String tableName;
    private List<String> columns;

    private Table(String tableName, List<String> columns) {
        this.tableName = tableName;
        this.columns = columns;

    }

    public static Table fromString(String text) {
        if (text != null) {
            for (Table type : Table.values()) {
                if (text.equalsIgnoreCase(type.getTableName())) {
                    return type;
                }
            }
        }
        return null;
    }

    public List<String> getTableColumns() {
        return this.columns;
    }

    public String getTableName() {

        return this.tableName;
    }

    private static class COLUMNS {

        private static final List<String> REMOVAL_DETAILS = Arrays.asList("Campaign", "Product", "Link", "Type", "Referrer", "Takedown sent",
                "Status");

    }

}
