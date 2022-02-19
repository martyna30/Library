package com.library.controller;

import com.library.domain.Author;
import com.library.domain.AuthorDto;

import com.library.exception.OrderChecks;
import com.library.mapper.AuthorMapper;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<AuthorDto> getAuthors() {
        return authorMapper.mapToAuthorsDtoList(authorService.getAllAuthors());
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
    public void deleteAuthor(@RequestParam Long authorId) {
        authorService.deleteAuthor(authorId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateAuthor")
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorMapper.mapToAuthorDto(authorService.saveAuthor(authorMapper.mapToAuthor(authorDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createAuthor")
    public ResponseEntity<Object> createAuthor(@Validated(value = {OrderChecks.class}) @Valid @RequestBody AuthorDto authorDto, Errors errors) {
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
            return new ResponseEntity<>(errorsAuthorsMap, HttpStatus.BAD_REQUEST);
        }
        authorService.saveAuthor(authorMapper.mapToAuthor(authorDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method=RequestMethod.GET, value = "findIdByName", consumes = APPLICATION_JSON_VALUE)
    public Optional<Author>findIdByName(@RequestParam String surname, String forename)  {
        return authorService.findAuthorByName(surname, forename);
    }

}
