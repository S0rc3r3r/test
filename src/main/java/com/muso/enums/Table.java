package com.muso.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.persistence.PersistenceManager;

public enum Table {
    REMOVAL_DETAILS("Removal Details", COLUMNS.REMOVAL_DETAILS),
    PRODUCTS("Products", COLUMNS.PRODUCTS),
    CAMPAIGNS("Campaigns", COLUMNS.CAMPAIGNS);

    private String tableName;
    private List<String> columns;
    private static final Logger LOGGER = LoggerFactory.getLogger(Table.class);

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

    public ArrayList<String> getTableColumns() {
        final ArrayList<String> columns = new ArrayList<String>(this.columns);

        if (this == CAMPAIGNS || this == PRODUCTS) {
            columns.add(2, PersistenceManager.getInstance().getDateRange());
            return columns;
        }
        return columns;
    }

    public String getTableName() {

        return this.tableName;
    }

    public boolean areTableHeadersValid(List<String> headers) {

        if (headers.size() != this.getTableColumns().size()) {
            LOGGER.error("Headers size for table {} don't match. Expected {} but found {}", this.getTableName(), this.getTableColumns().size(),
                    headers.size());
            return false;
        }

        for (String header : headers)
            if (!this.getTableColumns().contains(header)) {
                LOGGER.warn("Header name {} is invalid for table {}", header, this.getTableName());
                return false;
            }

        return true;

    }

    private static class COLUMNS {

        private static final List<String> REMOVAL_DETAILS = Arrays.asList("Campaign", "Product", "Link", "Type", "Referrer", "Takedown sent",
                "Status");
        private static final List<String> CAMPAIGNS = Arrays.asList("Campaign", "All Time", "Last Week");

        private static final List<String> PRODUCTS = Arrays.asList("Product", "All Time", "Last Week");

    }

}
