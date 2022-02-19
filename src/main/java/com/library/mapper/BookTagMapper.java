package com.library.mapper;

import com.library.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookTagMapper {

    @Autowired
    BookMapper bookMapper;

    public BookTag mapToBookTag(BookTagDto bookTagDto) {
        return new BookTag(
                bookTagDto.getId(),
                bookTagDto.getLiteraryGenre()
        );
    }

    public BookTagDto mapToBookTagDto(BookTag bookTag) {
        return new BookTagDto(
                bookTag.getId(),
                bookTag.getLiteraryGenre()
        );
    }

    public List<BookTag> mapToBookTagsList(final List<BookTagDto> booktagsDto){
        return booktagsDto.stream()
                .map(bookTagDto -> new BookTag(
                        bookTagDto.getId(),
                        bookTagDto.getLiteraryGenre()))
                .collect(Collectors.toList());
    }

    public List<BookTagDto> mapToBookTagsDtoList(final List<BookTag> booktags){

        return booktags.stream()
                .map(bookTag -> new BookTagDto(
                        bookTag.getId(),
                        bookTag.getLiteraryGenre()))
                .collect(Collectors.toList());
    }

}
