package com.library.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Could not find book: ");
    }
}
