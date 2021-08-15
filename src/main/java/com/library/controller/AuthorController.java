package com.library.controller;

import com.library.domain.Author;
import com.library.domain.AuthorDto;

import com.library.mapper.AuthorMapper;
import com.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public Author createAuthor( @Valid @RequestBody AuthorDto authorDto) {
        return authorService.saveAuthor(authorMapper.mapToAuthor(authorDto));
    }

    @RequestMapping(method=RequestMethod.GET, value = "findIdByName", consumes = APPLICATION_JSON_VALUE)
    public Optional<Author>findIdByName(@RequestParam String surname, String forename)  {
        return authorService.findAuthorByName(surname, forename);
    }

}
