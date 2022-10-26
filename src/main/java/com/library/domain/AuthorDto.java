package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.exception.LengthOfCharacters;
import com.library.exception.NotEmptyGroup;
import com.library.exception.UniqueFormat;
import com.library.validation.AuthorConstraint;
import com.library.validation.SignatureConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)


public class AuthorDto {

    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length surname of the author must be between 3 and 30 characters")
    private String surname;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length forename of the author must be between 3 and 30 characters")
    private String forename;
    //private List<BookDto> books = new ArrayList<>();
}
