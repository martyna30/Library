package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentalDto {
    private Long id;
    private LocalDate startingDate;
    private LocalDate finishDate;
    private int amountOfBorrowedBooks;
    @Enumerated(EnumType.STRING)
    private Status status;
    //Reader reader;
    //Book book;
}
