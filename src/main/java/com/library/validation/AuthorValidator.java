package com.library.validation;

import com.library.domain.Author;
import com.library.domain.AuthorDto;
import com.library.domain.Book;
import com.library.service.AuthorService;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class AuthorValidator implements ConstraintValidator<AuthorConstraint, AuthorDto> {
    private String[] fields;
    //private String secondField;

    @Autowired
    AuthorService authorService;

    @Override
    public void initialize(AuthorConstraint authorConstraint) {
        this.fields= authorConstraint.field();
        //this.secondField = authorConstraint.secondField();
    }

    @Override
    public boolean isValid(AuthorDto authorDto, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Author> authorFromDB = authorService.findAuthorByName(authorDto.getForename(), authorDto.getSurname());

        if(authorFromDB.isPresent()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fields[0])
                    .addConstraintViolation();
            return false;
        }
        else {
            return true;
        }
    }
}
