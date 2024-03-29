package com.library.controller;

import com.library.domain.Author;
import com.library.domain.AuthorDto;

import com.library.domain.ListAuthorsDto;
import com.library.exception.AuthorNotFoundException;
import com.library.validationGroup.OrderChecks;
import com.library.mapper.AuthorMapper;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InvalidClassException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getAuthors")
    public ResponseEntity<ListAuthorsDto> getAuthors(@RequestParam int page, @RequestParam int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(
                new ListAuthorsDto(
                        authorService.getCount(),
                        authorMapper.mapToAuthorsDtoList(authorService.getAllAuthors(pageRequest))
                )
        );
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAuthorsForenameWithSpecifiedCharacters")
    public List<AuthorDto> getAuthorsForenameWithSpecifiedCharacters(@RequestParam String forename) {
        return authorMapper.mapToAuthorsDtoList(authorService.getAuthorsForenameWithSpecifiedCharacters(forename));
    }

   @RequestMapping(method = RequestMethod.GET, value = "getAuthorsSurnameWithSpecifiedCharacters")
   public List<AuthorDto> getAuthorsSurnameWithSpecifiedCharacters(@RequestParam String surname) {
       return authorMapper.mapToAuthorsDtoList(authorService.getAuthorsSurnameWithSpecifiedCharacters(surname));
   }
    @RequestMapping(method = RequestMethod.GET, value = "getAuthor")
    public AuthorDto getAuthor(@RequestParam Long authorId) throws AuthorNotFoundException {
        return authorMapper.mapToAuthorDto(authorService.getAuthor(authorId).orElseThrow(AuthorNotFoundException:: new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAuthor")
    public ResponseEntity<?> deleteAuthor(@RequestParam Long authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateAuthor")
    public ResponseEntity<Object> updateAuthor(@Validated(value = {OrderChecks.class}) @Valid @RequestBody AuthorDto authorDto, Errors errors) throws InvalidClassException {
        if(errors.hasErrors()) {
            Map<String, ArrayList<Object>> errorsAuthorsMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                if(!errorsAuthorsMap.containsKey(key)) {
                    errorsAuthorsMap.put(key, new ArrayList<>());
                }
                errorsAuthorsMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsAuthorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsAuthorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        authorMapper.mapToAuthorDto(authorService.saveAuthor(authorMapper.mapToAuthor(authorDto)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createAuthor")
    public ResponseEntity<Object> createAuthor(@Validated(value = {OrderChecks.class}) @Valid @RequestBody AuthorDto authorDto, Errors errors) throws InvalidClassException {
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
        authorService.saveAuthor(authorMapper.mapToAuthor(authorDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.GET, value = "findIdByName", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Author>findAuthorByName(@RequestParam String surname, String forename)  {
        return authorService.findAuthorByName(surname, forename)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
