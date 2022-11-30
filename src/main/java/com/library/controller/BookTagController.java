package com.library.controller;

import com.library.domain.BookTagDto;
import com.library.exception.BookTagNotFoundException;
import com.library.mapper.BookTagMapper;
import com.library.service.BookTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class BookTagController {
    @Autowired
    private BookTagService bookTagService;
    @Autowired
    private BookTagMapper bookTagMapper;


    @RequestMapping(method = RequestMethod.GET, value = "getBooksTags")
    public List<BookTagDto> getBooksTags() {
        return bookTagMapper.mapToBookTagsDtoList(bookTagService.getAllBookTags());
    }


    @RequestMapping(method = RequestMethod.GET, value = "getBooksTagsWithSpecifiedCharacters")
    public List<BookTagDto> getBooksTagsWithSpecifiedCharacters(@RequestParam String bookTag) {
        return bookTagMapper.mapToBookTagsDtoList(bookTagService.getBooksTagsWithSpecifiedCharacters(bookTag));
    }
    @RequestMapping(method = RequestMethod.GET, value = "getBookTag")
    public BookTagDto getBookTag(@RequestParam Long bookTagId) throws BookTagNotFoundException {
        return bookTagMapper.mapToBookTagDto(bookTagService.getBookTag(bookTagId).orElseThrow(BookTagNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBookTag", consumes = APPLICATION_JSON_VALUE )
    public void deleteBookTag(@RequestParam Long bookTagId) {
        bookTagService.deleteBookTag(bookTagId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBookTag")
    public BookTagDto updateBookTag(@Valid @RequestBody BookTagDto bookTagDto) {
        return bookTagMapper.mapToBookTagDto(bookTagService.saveBookTag(bookTagMapper.mapToBookTag(bookTagDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBookTag", consumes = APPLICATION_JSON_VALUE)
    public void createBookTag(@Valid @RequestBody BookTagDto bookTagDto) {
        bookTagService.saveBookTag(bookTagMapper.mapToBookTag(bookTagDto));
    }
}

