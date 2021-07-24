package com.library.validator;


import com.library.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;
@Component
public class ValidationClass {
    private final Validator validator;

    public ValidationClass() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static void main(String[] args) {

        ValidationClass validation = new ValidationClass();
        validation.validate();
    }

    public void validate() {
        AuthorDto authorDto = new AuthorDto();
        //BookTagDto bookTagDto = new BookTagDto();
        BookDto bookDto = new BookDto();
        Set<ConstraintViolation<AuthorDto>> errorsListAuthors = validator.validate(authorDto);
        Set<ConstraintViolation<BookDto>> errorsListBooks = validator.validate(bookDto);
        errorsListAuthors.forEach(error -> System.err.println(error.getPropertyPath() + "" + error.getMessage()));
        errorsListBooks.forEach(error -> System.err.println(error.getPropertyPath() + "" + error.getMessage()));
    }
}




