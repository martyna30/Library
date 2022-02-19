package com.library.service;

import com.library.domain.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();

    }
    public Author saveAuthor(final Author author) {
        Optional<Author> authorOptional = authorRepository.findAuthorByName(author.getForename(), author.getSurname());
        if(authorOptional.isPresent()) {
            System.out.println("Entry already exist");
        }
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(final Long id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthor(final Long id) {
        authorRepository.deleteById(id);
    }


    public Optional<Author> findAuthorByName(final String forename, final String surname) {
      Optional<Author> authorOptional = authorRepository.findAuthorByName(forename,surname);
      return authorOptional;
    }

    public List<Author> getAuthorsForenameWithSpecifiedCharacters(String forename) {
        return authorRepository.findAuthorsByForename(forename);
    }

    public List<Author> getAuthorsSurnameWithSpecifiedCharacters(String surname) {
       List<Author> authors= authorRepository.findAuthorsBySurname(surname);
       return authors;
    }
}
