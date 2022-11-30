package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.validationGroup.LengthOfCharacters;
import com.library.validationGroup.NotEmptyGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookTagDto {
    private Long id;
    @NotBlank(groups= NotEmptyGroup.class, message ="Field can remain empty")
    @Size(groups= LengthOfCharacters.class, min = 3, max = 30, message = "Length of literary genre must be between 3 and 30 characters")
    private String literaryGenre;
    //private List<BookDto> books = new ArrayList<>();
}
