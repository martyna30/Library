package com.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest webRequest) {
        List<String>validateErrors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        LOGGER.info("validation error list: " + validateErrors);

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), validateErrors);

        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), webRequest);
    }



    /*private ResponseEntity<Object> response(Exception ex, WebRequest webrequest, HttpStatus status,
                                            String message) {
        return handleExceptionInternal(ex, message, header(), status, webrequest);
    }*/








        /*@ExceptionHandler({ UnauthorizedException.class, TaskNotFoundException.class })
        public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
            HttpHeaders headers = new HttpHeaders();
            if (ex instanceof UnauthorizedException) {
                HttpStatus status = HttpStatus.NOT_FOUND;
                UnauthorizedException ue = (UnauthorizedException) ex;

                return handleUserNotFoundException(ue, headers, status, request);
            } else if (ex instanceof TaskNotFoundException) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                TaskNotFoundException tnfe = (TaskNotFoundException) ex;

                return handleContentNotAllowedException(tnfe, headers, status, request);
            } else {
                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                return handleExceptionInternal(ex, null, headers, status, request);
            }
        }

    }
}*/
}
