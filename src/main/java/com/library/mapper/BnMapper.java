package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.bn.BnBookDto;
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

    public Book mapToBook(BnBookDto bnBookDto) {
        return new Book(
                bnBookDto.getId(),
                bnBookDto.getTitle(),
                bnBookDto.getYearOfPublication(),
                bnBookDto.getSignature(),
                bookTagMapper.mapToBookTagsList(bnBookDto.getBooksTag()),
                authorMapper.mapToAuthorsList(bnBookDto.getAuthors())
        );
    }

    public List<Book> mapToBooklist(final List<BnBookDto>bnBookDtos) {
        return bnBookDtos.stream()
                .map(bnBookDto -> new Book(bnBookDto.getId(),
                        bnBookDto.getTitle(),
                        bnBookDto.getYearOfPublication(),
                        bnBookDto.getSignature(),
                        bookTagMapper.mapToBookTagsList(bnBookDto.getBooksTag()),
                        authorMapper.mapToAuthorsList(bnBookDto.getAuthors())))
                .collect(Collectors.toList());
    }


}
