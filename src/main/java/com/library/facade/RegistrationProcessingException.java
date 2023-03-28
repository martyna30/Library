package com.library.facade;

public class RegistrationProcessingException extends Exception{

    public static String ERR_EMAIL_ALREADY_EXIST = "Email already taken and confirmed";

    public static String ERR_TOKEN_NOT_CONFIRMED = "Email already taken and confirmed";

    public RegistrationProcessingException(String message) {
        super(message);
    }
}
