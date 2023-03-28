package com.library.facade;

import org.springframework.stereotype.Component;


public class RentalProcessingException extends Exception {
    public static String ERR_BOOK_NOT_FOUND = "Book not found";

    public static String ERR_LIST_OF_BORROWED_BOOKS_FOR_USER_NOT_FOUND = "List of borrowed books for user not found";

    public RentalProcessingException(String message) {
        super(message);
    }
}
