package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto {
    private Long id;
    private String title;
    private int yearOfPublication;
    private String signature;
    private int amountOfbook;
    private int amountOfborrowed;
    private List<BookTagDto> bookTags = new ArrayList<>();
    private List<AuthorDto> authors = new ArrayList<>();

}
