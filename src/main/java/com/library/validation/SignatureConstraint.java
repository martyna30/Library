package com.library.validation;

import com.library.domain.BookDto;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.beans.PropertyEditor;
import java.lang.annotation.*;
import java.util.List;
import java.util.Map;

@Documented
@Constraint(validatedBy = SignatureValidator.class )
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureConstraint {
    String message() default "book already exist";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

  }
