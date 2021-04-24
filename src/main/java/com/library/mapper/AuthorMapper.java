package com.library.mapper;

import com.library.domain.Author;
import com.library.domain.AuthorDto;
import com.library.domain.Book;
import com.library.domain.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    @Autowired
    BookMapper bookMapper;

    public Author mapToAuthor(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getSurname(),
                authorDto.getForename()
                //bookMapper.mapToBookList(authorDto.getBooks())
        );
    }

    public AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getSurname(),
                author.getForename()
                //bookMapper.mapToBookDtoList(author.getBooks())
        );
    }

    public List<Author> mapToAuthorsList(final List<AuthorDto>authorListDto){
        return authorListDto.stream()
                .map(authorDto-> new Author(
                        authorDto.getId(),
                        authorDto.getSurname(),
                        authorDto.getForename()))
                        //bookMapper.mapToBookList(authorDto.getBooks())))
                .collect(Collectors.toList());
    }

    public List<AuthorDto> mapToAuthorsDtoList(final List<Author>authorList){
        return authorList.stream()
                .map(author-> new AuthorDto(
                        author.getId(),
                        author.getSurname(),
                        author.getForename()))

                .collect(Collectors.toList());
    }
}
