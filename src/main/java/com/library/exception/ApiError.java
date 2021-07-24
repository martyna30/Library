package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;

public class ApiError {

private Errors errors;

public ApiError(Errors errors){
    super();
    this.errors = errors;
}
}

