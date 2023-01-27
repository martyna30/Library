package com.library.mapper;

import com.library.domain.rental.RentalBook;
import com.library.domain.rental.RentalBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalBookMapper {
    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;
    RentalBookDto mapToRentalBookDto(RentalBook rentalBook) {
        return new RentalBookDto(
        bookMapper.mapToBookDto(rentalBook.getBook()),
        userMapper.mapToUserDto(rentalBook.getUser())
        );
    }

    RentalBook mapToRentalBook(RentalBookDto rentalBookDto) {
        return new RentalBook(
                bookMapper.mapToBook(rentalBookDto.getBookDto()),
                userMapper.mapToUser(rentalBookDto.getUserDto())
        );
    }

}
