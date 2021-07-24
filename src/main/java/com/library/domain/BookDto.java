package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private Long id;
    @Pattern(regexp = "^[A-Z][a-zA-Z]{2,}$", message = "Must być z dużej litery i posiadac same litery")
    @Size(min = 3, max = 30, message = "Length title of the book must be beetween 3 and 30 characters")
    @NotBlank(message = "prosze podac tytuł")
    private String title;
    @Digits(integer = 4, fraction = 0)
    private int yearOfPublication;
    private String signature;
    private int amountOfbook;
    private int amountOfborrowed;
    private List<BookTagDto> bookTags = new ArrayList<>();
    private List<AuthorDto> authors = new ArrayList<>();

}
