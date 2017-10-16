package com.muso.exceptions;

@SuppressWarnings("serial")
public class InvalidOptionException extends RuntimeException {

    public InvalidOptionException(String message) {
        super(message);
    }
}
