package com.library.controller;

import com.library.domain.*;
import com.library.mapper.BookTagMapper;
import com.library.service.BookTagService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookTagControllerTest {

    @InjectMocks
    BookTagController bookTagController;

    @Mock
    private BookTagService bookTagService;
    @Mock
    private BookTagMapper bookTagMapper;

    @Mock
    Errors errors;

    @Test
    public void testGetBookTags() throws Exception {
        //given
        List<BookTag> bookTags = new ArrayList<>();
        BookTagDto bookTagDto = new BookTagDto();
        bookTagDto.setId(1L);
        List<BookTagDto> bookTagDtos = new ArrayList<>();
        bookTagDtos.add(bookTagDto);

        when(bookTagService.getAllBookTags()).thenReturn(bookTags);
        when(bookTagMapper.mapToBookTagsDtoList(bookTags)).thenReturn(bookTagDtos);

        //When
        List<BookTagDto>bookTagResultlist = bookTagController.getBooksTags(bookTagDto.getId());

        //Then
        Assert.assertNotNull(bookTagResultlist);
        Assert.assertEquals(1, bookTagDtos.size());
        Assert.assertEquals(1, bookTagResultlist.size());
    }


    @Test
    public void getBooksTagsWithSpecifiedCharacters() throws Exception {
        List<BookTagDto> getBooksTagsWithSpecifiedCharacters;

        //given
        String literaryGenre = "test";
       // BookTag bookTag = new BookTag();
       // bookTag.setLiteraryGenre(literaryGenre);
        List<BookTag> bookTags = new ArrayList<>();
        BookTagDto bookTagDto = new BookTagDto();
        bookTagDto.setLiteraryGenre(literaryGenre);
        List<BookTagDto> bookTagDtos = new ArrayList<>();
        bookTagDtos.add(bookTagDto);

        when(bookTagService.getBooksTagsWithSpecifiedCharacters(literaryGenre)).thenReturn(bookTags);
        when(bookTagMapper.mapToBookTagsDtoList(bookTags)).thenReturn(bookTagDtos);

        //When
        List<BookTagDto>bookTagResultlist = bookTagController.getBooksTagsWithSpecifiedCharacters(literaryGenre);

        //Then
        Assert.assertNotNull(bookTagResultlist);
        Assert.assertEquals(1, bookTagDtos.size());
        Assert.assertEquals(1, bookTagResultlist.size());
        Assert.assertEquals("test", bookTagResultlist.stream()
                .map(bookTagDto1 -> bookTagDto1.getLiteraryGenre())
                .collect(Collectors.joining()));
    }

    @Test
    public void testGetBookTag() throws Exception {
        //given
        BookTag bookTag = new BookTag();
        BookTagDto bookTagDto = new BookTagDto();
        Long id = 1L;
        bookTagDto.setId(id);

        when(bookTagService.getBookTag(bookTagDto.getId())).thenReturn(Optional.of(bookTag));
        when(bookTagMapper.mapToBookTagDto(bookTag)).thenReturn(bookTagDto);
        //When
        BookTagDto bookTagDtoResult = bookTagController.getBookTag(bookTagDto.getId());

        //Then
        Assert.assertNotNull(bookTagDtoResult);
        Assert.assertEquals(Optional.of(1L).get(), bookTagDtoResult.getId());
        Assert.assertEquals(bookTagDto.getId(), bookTagDtoResult.getId());

    }

    @Test
    public void testDeleteBookTag() {
        //given
        BookTag bookTag = new BookTag();
        Long id = 1L;
        bookTag.setId(id);

        //When
        ResponseEntity<?> responseEntity = bookTagController.deleteBookTag(bookTag.getId());

        //Then
        assertEquals(HttpStatus.NO_CONTENT.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void testUpdateBookTag() {
        //given
        BookTagDto modified = new BookTagDto();
        String literaryGenre = "test";
        modified.setLiteraryGenre(literaryGenre);

        //when
        ResponseEntity<Object> responseEntity = bookTagController.updateBookTag(modified, errors);

        //Then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(modified);
        Assert.assertEquals("test", modified.getLiteraryGenre());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testCreateBookTag() {
        //given
        BookTagDto bookTagDto= new BookTagDto();
        String literaryGenre = "test";
        bookTagDto.setLiteraryGenre(literaryGenre);

        //when
        ResponseEntity<Object> responseEntity = bookTagController.createBookTag(bookTagDto, errors);

        //Then
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(bookTagDto);
        Assert.assertEquals("test", bookTagDto.getLiteraryGenre());
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
    }
}
