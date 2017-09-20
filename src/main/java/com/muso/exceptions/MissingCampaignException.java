package com.muso.exceptions;

@SuppressWarnings("serial")
public class MissingCampaignException extends RuntimeException {

    public MissingCampaignException(String message) {
        super(message);
    }
}