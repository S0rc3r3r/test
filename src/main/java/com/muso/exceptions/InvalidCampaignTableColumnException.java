package com.muso.exceptions;

@SuppressWarnings("serial")
public class InvalidCampaignTableColumnException extends RuntimeException {

    public InvalidCampaignTableColumnException(String message) {
        super(message);
    }
}