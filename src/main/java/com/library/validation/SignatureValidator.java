package com.library.validation;

import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.service.BookService;
import com.library.service.BookTagService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Optional;

public class SignatureValidator implements ConstraintValidator<SignatureConstraint, BookDto> {
    private String field;

    @Autowired
    BookService bookService;


    @Override
    public void initialize(SignatureConstraint signatureConstraint) {
        this.field = signatureConstraint.field();
    }

    @Override
    public boolean isValid(final BookDto bookDto, final ConstraintValidatorContext context) {
        Optional<Book> bookFromDB = bookService.findBookBySignature(bookDto.getSignature());
        if (bookFromDB.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(field)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

 }

