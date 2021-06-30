package com.library.mapper;


import com.library.domain.Book;
import com.library.domain.bn.ExternalResponseBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.util.List;
import java.util.stream.Collectors;

@Component
public class BnMapper {

    @Autowired
    BookTagMapper bookTagMapper;

    @Autowired
    AuthorMapper authorMapper;

    public Book mapToBook(ExternalResponseBookDto externalResponseBookDto) {
        return new Book(
                externalResponseBookDto.getId(),
                externalResponseBookDto.getYearOfPublication(),
                externalResponseBookDto.getSignature(),
                bookTagMapper.mapToBookTagsList(externalResponseBookDto.getBooksTag()),
                authorMapper.mapToAuthorsList(externalResponseBookDto.getAuthors())
        );
    }

    public ExternalResponseBookDto mapToBookDto(Book book) {
        return new ExternalResponseBookDto(
                book.getId(),
                book.getYearOfPublication(),
                book.getSignature(),
                bookTagMapper.mapToBookTagsDtoList(book.getBookTags()),
                authorMapper.mapToAuthorsDtoList(book.getAuthors())
        );
    }

    public List<Book> mapToBooklist(final List<ExternalResponseBookDto> externalResponseBookDtos) {
        return externalResponseBookDtos.stream()
                .map(bnBookDto -> new Book(bnBookDto.getId(),
                        bnBookDto.getYearOfPublication(),
                        bnBookDto.getSignature(),
                        bookTagMapper.mapToBookTagsList(bnBookDto.getBooksTag()),
                        authorMapper.mapToAuthorsList(bnBookDto.getAuthors())))
                .collect(Collectors.toList());
    }

    public List<ExternalResponseBookDto> mapToBnBookDtolist(final List<Book>books) {
        return books.stream()
                .map(book -> new ExternalResponseBookDto(book.getId(),
                        book.getYearOfPublication(),
                        book.getSignature(),
                bookTagMapper.mapToBookTagsDtoList(book.getBookTags()),
                authorMapper.mapToAuthorsDtoList(book.getAuthors())))
                .collect(Collectors.toList());
    }


}
