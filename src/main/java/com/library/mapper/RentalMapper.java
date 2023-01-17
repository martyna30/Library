package com.library.mapper;


import com.library.domain.Rental;
import com.library.domain.RentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    UserMapper userMapper;


    @Autowired
    LoggedUserMapper loggedUserMapper;

    public Rental mapToRental(RentalDto rentalDto) {
        return new Rental(
                rentalDto.getTitle(),
                rentalDto.getStartingDate(),
                rentalDto.getFinishDate(),
                rentalDto.getAmountOfBorrowedBooks(),
                rentalDto.getStatus(),
                this.loggedUserMapper.mapToUser(rentalDto.getLoggedUserDto()),
                this.bookMapper.mapToBook(rentalDto.getBookDto())

        );
    }

    public RentalDto mapToRentalDto(Rental rental) {
        return new RentalDto(
                rental.getTitle(),
                rental.getStartingDate(),
                rental.getFinishDate(),
                rental.getAmountOfBorrowedBooks(),
                rental.getStatus(),
                loggedUserMapper.mapToLoggedUserDto(rental.getUser()),
                bookMapper.mapToBookDto(rental.getBook())
        );

    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> borrowedBooks) {
        return borrowedBooks.stream()
                .map(rental-> new RentalDto(
                        rental.getTitle(),
                        rental.getStartingDate(),
                        rental.getFinishDate(),
                        rental.getAmountOfBorrowedBooks(),
                        rental.getStatus(),
                        loggedUserMapper.mapToLoggedUserDto(rental.getUser()),
                        bookMapper.mapToBookDto(rental.getBook())))
                .collect(Collectors.toList());
    }

    public List<Rental> mapToRentalList(final List<RentalDto> borrowedBooks) {
        return borrowedBooks.stream()
                .map(rentalDto-> new Rental(
                        rentalDto.getTitle(),
                        rentalDto.getStartingDate(),
                        rentalDto.getFinishDate(),
                        rentalDto.getAmountOfBorrowedBooks(),
                        rentalDto.getStatus(),
                        this.loggedUserMapper.mapToUser(rentalDto.getLoggedUserDto()),
                        this.bookMapper.mapToBook(rentalDto.getBookDto())))
                .collect(Collectors.toList());
    }
}
