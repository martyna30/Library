package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.exception.*;

import com.library.validation.SignatureConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;


import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.executable.ValidateOnExecution;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@SignatureConstraint(groups = UniqueFormat.class, field = "signature")
public class BookDto {
    private Long id;

    @NotBlank(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length title of the book must be between 3 and 30 characters")
    private String title;
    //@NotBlank(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Min(groups= Format.class, value = 1900, message="Year of publication must be after 1900")
    @Digits(groups= LengthOfCharacters.class, integer = 4, fraction = 0, message = "Year of publication must contain the four numbers between 0-9.")
    private Integer yearOfPublication;

    @NotBlank(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Pattern(groups= Format.class, regexp = "^[A-Z]{1,}( ?[1-9][0-9]{1,})(/[0-9]{4,})$" , message= "Signature has a bad format")
    //@SignatureField(groups= UniqueFormat.class,field = "signature", message= "Signature must by unique")
    //@AssertTrue(groups = UniqueFormat.class, message = "Signature must by unique")
    private String signature;

    //private Integer amountOfbook;
    //private Integer amountOfborrowed;
    @Valid
    private List<BookTagDto> bookTags = new ArrayList<>();
    @Valid
    private List<AuthorDto> authors = new ArrayList<>();

}
