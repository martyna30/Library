package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListAuthorsDto {

    private long total = 0;

    private List<AuthorDto> authors = new ArrayList<>();

    public ListAuthorsDto(long total, List<AuthorDto> authors) {
        this.total = total;
        this.authors = authors;
    }

}
