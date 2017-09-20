package com.muso.exceptions;

@SuppressWarnings("serial")
public class MissingProductException extends RuntimeException {

    public MissingProductException(String message) {
        super(message);
    }
}