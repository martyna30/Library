package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    @Autowired
    BookTagMapper bookTagMapper;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RentalMapper rentalMapper;

    @Autowired
    ObjectMapper objectMapper;

    /*public Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getYearOfPublication(),
                bookDto.getSignature(),
                bookTagMapper.mapToBookTagsList(bookDto.getBookTags()),
                authorMapper.mapToAuthorsList(bookDto.getAuthors()),
                objectMapper.mapToObject(bookDto.getObjectsNameDto())
        );
    }*/

    public Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getYearOfPublication(),
                bookDto.getSignature(),
                bookTagMapper.mapToBookTagsList(bookDto.getBookTags()),
                authorMapper.mapToAuthorsList(bookDto.getAuthors())
                //objectMapper.mapToObject(bookDto.getObjectsNameDto())
        );
    }

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getYearOfPublication(),
                book.getSignature(),
                bookTagMapper.mapToBookTagsDtoList(book.getBookTags()),
                authorMapper.mapToAuthorsDtoList(book.getAuthors())
                // rentalMapper.mapToRentalDtoList(book.getBorrowedBooks()),
                //objectMapper.mapToObjectDto(book.getObjectsName())
        );
    }

    public List<Book> mapToBookList(final List<BookDto> bookDtoList) {
        return bookDtoList.stream()
                .map(bookDto -> new Book(
                        bookDto.getId(),
                        bookDto.getTitle(),
                        bookDto.getYearOfPublication(),
                        bookDto.getSignature(),
                        bookTagMapper.mapToBookTagsList(bookDto.getBookTags()),
                        authorMapper.mapToAuthorsList(bookDto.getAuthors())))
                        //bookDto.getBorrowedBooks(),
                        //objectMapper.mapToObject(bookDto.getObjectsNameDto())))
                .collect(Collectors.toList());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {

        List<BookDto> bookDtos = bookList.stream()
                .map(book -> new BookDto(
                        book.getId(),
                        book.getTitle(),
                        book.getYearOfPublication(),
                        book.getSignature(),
                        bookTagMapper.mapToBookTagsDtoList(book.getBookTags()),
                        authorMapper.mapToAuthorsDtoList(book.getAuthors())))
                        //rentalMapper.mapToRentalDtoList(book.getBorrowedBooks()),
                        //objectMapper.mapToObjectDto(book.getObjectsName())))
                .collect(Collectors.toList());
        return bookDtos;
    }
}

