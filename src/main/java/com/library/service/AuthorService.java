package com.library.service;

import com.library.domain.Author;
import com.library.domain.ObjectName;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.transaction.Transactional;
import java.io.InvalidClassException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ObjectService objectService;

    public List<Author> getAllAuthors(Pageable pageable) {
        Page<Author> authorPage = authorRepository.findAll(pageable);
        return authorPage.getContent();
    }


    public long getCount() {
        return authorRepository.count();
    }
    @Transactional
    public Author saveAuthor(final Author author) throws InvalidClassException {
        Optional<Author> authorOptional = authorRepository.findAuthorByName(author.getForename(), author.getSurname());
        if(authorOptional.isPresent()) {
            author.setId(authorOptional.get().getId());
            Optional<ObjectName> optionalObjectNameAuthor = objectService.findObjectWithSpecifiedTitleOrAuthor(
                    authorOptional.get().getObjectNameAuthor().getName());
            if (optionalObjectNameAuthor.isPresent()) {
                author.setObjectNameAuthor(authorOptional.get().getObjectNameAuthor());
            }
            else {
                StringBuilder forename = new StringBuilder(author.getForename());
                StringBuilder surname = new StringBuilder(author.getSurname());
                StringBuilder names = forename.append(surname);
                String name = names.toString();
                ObjectName objectNameAuthor = new ObjectName(name,author);
                author.setObjectNameAuthor(objectNameAuthor);
            }
        }
        else {
            StringBuilder forename = new StringBuilder(author.getForename());
            StringBuilder surname = new StringBuilder(author.getSurname());
            StringBuilder names = forename.append(surname);
            String name = names.toString();
            ObjectName objectNameAuthor = new ObjectName(name,author);
            author.setObjectNameAuthor(objectNameAuthor);
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
     return authorRepository.findAuthorByName(forename,surname);
    }



    public List<Author> getAuthorsForenameWithSpecifiedCharacters(String forename) {
        return authorRepository.findAuthorsByForename(forename);
    }

    public List<Author> getAuthorsSurnameWithSpecifiedCharacters(String surname) {
       List<Author> authors= authorRepository.findAuthorsBySurname(surname);
       return authors;
    }






}
