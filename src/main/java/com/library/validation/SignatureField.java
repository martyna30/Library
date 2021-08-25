package com.library.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
//@Constraint(validatedBy = SignatureFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureField {
    String message() default "already exist";

   String field();


}

