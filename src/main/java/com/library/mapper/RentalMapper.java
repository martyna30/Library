package com.library.mapper;

import com.library.domain.ReaderDto;
import com.library.domain.Rental;
import com.library.domain.RentalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public Rental mapToRental(RentalDto rentalDto) {
        return new Rental(
                rentalDto.getId(),
                rentalDto.getStartingDate(),
                rentalDto.getFinishDate(),
                rentalDto.getStatus(),
                rentalDto.getAmountOfBorrowedBooks()
        );
    }

    public RentalDto mapToRentalDto(Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getStartingDate(),
                rental.getFinishDate(),
                rental.getAmountOfBorrowedBooks(),
                rental.getStatus()
        );

    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> borrowedBooks) {
        return borrowedBooks.stream()
                .map(rental-> new RentalDto(
                        rental.getId(),
                        rental.getStartingDate(),
                        rental.getFinishDate(),
                        rental.getAmountOfBorrowedBooks(),
                        rental.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Rental> mapToRentalList(final List<RentalDto> borrowedBooks) {
        return borrowedBooks.stream()
                .map(rentalDto-> new Rental(
                        rentalDto.getId(),
                        rentalDto.getStartingDate(),
                        rentalDto.getFinishDate(),
                        rentalDto.getStatus(),
                        rentalDto.getAmountOfBorrowedBooks()))
                .collect(Collectors.toList());
    }
}
