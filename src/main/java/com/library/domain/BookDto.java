package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private Long id;

    @NotBlank(message = "1.Pole nie może być puste")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+$", message = "2.Must być z dużej litery i posiadac same litery")
    @Size(min = 3, max = 30, message = "3.Length title of the book must be beetween 3 and 30 characters")
    private String title;
    @Min(value = 1900, message="zły rok")
    private int yearOfPublication;
    private String signature;
    private int amountOfbook;
    private int amountOfborrowed;
    private List<BookTagDto> bookTags = new ArrayList<>();
    private List<AuthorDto> authors = new ArrayList<>();

}
