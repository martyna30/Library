package com.library.controller;

public class EmailNotFoundException extends  RuntimeException {

    public EmailNotFoundException(String s) {
        super("Could not find user with such email");
    }
}
