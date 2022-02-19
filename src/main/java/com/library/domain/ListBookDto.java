package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListBookDto {

    private long total = 0;

    private List<BookDto> books = new ArrayList<>();

    public ListBookDto(long total, List<BookDto> books) {
        this.total = total;
        this.books = books;
    }
}
