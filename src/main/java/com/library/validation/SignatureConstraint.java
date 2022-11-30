package com.library.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SignatureValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface SignatureConstraint {
    String message() default "Book with the same signature already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String field();

  }
