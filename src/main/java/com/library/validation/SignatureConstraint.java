package com.library.validation;

import com.library.exception.UniqueFormat;

import javax.validation.Constraint;
import javax.validation.constraints.NotBlank.List;

import javax.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Documented
@Constraint(validatedBy = SignatureValidator.class )
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface SignatureConstraint {
    String message() default "Book with the same signature already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String field();

  }
