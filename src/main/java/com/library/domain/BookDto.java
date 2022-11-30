package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import com.library.validation.SignatureConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.validation.SignatureConstraint;
import com.library.validationGroup.Format;
import com.library.validationGroup.LengthOfCharacters;
import com.library.validationGroup.NotEmptyGroup;
import com.library.validationGroup.UniqueFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.*;

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
    //@Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length title of the book must be between 3 and 30 characters")
    private String title;

    @NotNull(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Min(groups= Format.class, value = 1900, message="Year of publication must be after 1900")
    @Digits(groups= LengthOfCharacters.class, integer = 4, fraction = 0, message = "Year of publication must contain the four numbers between 0-9.")
    private Integer yearOfPublication;

    @NotBlank(groups= NotEmptyGroup.class, message = "Field can remain empty")
    @Pattern(groups= Format.class, regexp = "^[A-Z]{1,}( ?[1-9][0-9]{1,})(/[0-9]{4,})$" , message= "Signature has a bad format")
    private String signature;

    @Valid
    private List<BookTagDto> bookTags = new ArrayList<>();
    @Valid
    private List<AuthorDto> authors = new ArrayList<>();

    //private List<RentalDto> borrowedBooks = new ArrayList<>();



}
