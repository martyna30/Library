package com.library.security;

import org.springframework.validation.Errors;

public class LoginAuthenticationValidationException extends Throwable {
    public LoginAuthenticationValidationException(String authentication_validation_failure, Errors errors) {
    }
}
