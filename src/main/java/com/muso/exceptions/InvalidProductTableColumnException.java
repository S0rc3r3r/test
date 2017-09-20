package com.muso.exceptions;

@SuppressWarnings("serial")
public class InvalidProductTableColumnException extends RuntimeException {

    public InvalidProductTableColumnException(String message) {
        super(message);
    }
}