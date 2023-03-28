package com.library.controller;

import com.library.domain.BookTagDto;
import com.library.exception.BookTagNotFoundException;
import com.library.mapper.BookTagMapper;
import com.library.service.BookTagService;
import com.library.validationGroup.OrderChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<BookTagDto> getBooksTags(Long id) {
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
    ResponseEntity<?>deleteBookTag(@RequestParam Long bookTagId) {
        bookTagService.deleteBookTag(bookTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBookTag")
    public ResponseEntity<Object> updateBookTag(@Validated(value = {OrderChecks.class}) @Valid @RequestBody BookTagDto bookTagDto, Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsAuthorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if(!errorsAuthorsMap.containsKey(key)) {
                    errorsAuthorsMap.put(key,new ArrayList<>());
                }
                errorsAuthorsMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsAuthorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsAuthorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        bookTagService.saveBookTag(bookTagMapper.mapToBookTag(bookTagDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBookTag", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createBookTag(@Validated(value = {OrderChecks.class}) @Valid @RequestBody BookTagDto bookTagDto, Errors errors) {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsAuthorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if(!errorsAuthorsMap.containsKey(key)) {
                    errorsAuthorsMap.put(key,new ArrayList<>());
                }
                errorsAuthorsMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsAuthorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsAuthorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        bookTagService.saveBookTag(bookTagMapper.mapToBookTag(bookTagDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

