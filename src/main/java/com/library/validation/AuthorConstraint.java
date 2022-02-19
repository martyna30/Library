package com.library.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AuthorValidator.class )
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorConstraint {
    String message() default "Author with the same name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String [] field();

}
