package com.library.domain.bn;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.bn.converter.StringToArrayConverter;
import com.library.bn.converter.StringToIntConverter;
import com.library.domain.AuthorDto;
import com.library.domain.BookTagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalResponseBookDto {  //klasna na ktorą mapujue odpowidz

    @JsonProperty("id")
    private Long id;

    //@JsonProperty("title")
    //private String title;

    @JsonDeserialize(converter = StringToIntConverter.class)
    @JsonProperty("publicationYear")
    private int yearOfPublication;

    @JsonProperty("nationalBibliographyNumber")
    private String signature;

    @JsonDeserialize(converter = StringToArrayConverter.class)
    @JsonProperty("genre")
    private List<BookTagDto> booksTag = new ArrayList<>();

    @JsonDeserialize(converter = StringToArrayConverter.class)
    @JsonProperty("author")
    private List<AuthorDto> authors = new ArrayList<>();
}
