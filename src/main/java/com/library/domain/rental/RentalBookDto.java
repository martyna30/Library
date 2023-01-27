package com.library.domain.rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.library.domain.BookDto;
import com.library.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentalBookDto {
    @Valid
    BookDto bookDto;

    UserDto UserDto;
}
