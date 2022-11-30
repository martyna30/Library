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

        //BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bookDto);
        //final Object firstField = beanWrapper.getPropertyValue(field);

        Optional<Book> bookFromDB = bookService.findBookBySignature(bookDto.getSignature());
        if (bookFromDB.isPresent()) {
            //signatureFromDB = bookFromDB.get().getSignature();
            //final Object verifyObjFromDB = beanWrapper.getPropertyValue(signatureFromDB);
            //BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bookDto);
            //final Object firstField = beanWrapper.getPropertyValue(field);

            //boolean isValid = !(firstField.equals(verifyObjFromDB));

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(field)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }



        //Object fieldvalue = new BeanWrapperImpl(bookDto)
        // .getPropertyValue(field);

        /*try {
            String mainField, secondField, message;
            Object firstObj, secondObj;

            final Class<?> clazz = bookDto.getClass();
            final Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(SignatureField.class)) {
                    mainField = field.getName();
                    secondField = field.getAnnotation(SignatureField.class).field();
                    message = field.getAnnotation(SignatureField.class).message();

                    if (message == null || "".equals(message))
                        message = "Fields " + mainField + " and " + secondField + " must match!";

                    firstObj = BeanUtils.getPropertyDescriptor(clazz, mainField);
                    secondObj = BeanUtils.getPropertyDescriptor(clazz, secondField);

                    boolean result = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
                    if (!result) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(message).addPropertyNode(mainField).addConstraintViolation();
                        break;
                    }
                }
            }*/



 }

