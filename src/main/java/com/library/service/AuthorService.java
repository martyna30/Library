package com.library.service;

import com.library.domain.Author;
import com.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();

    }
    public Author saveAuthor(final Author author) {
        return  authorRepository.save(author);
    }

    public Optional<Author> getAuthor(final Long id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthor(final Long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Optional<Long> getIdByAuthorName(final String surname, final String forename) {
      Optional<Long>id = authorRepository.findIdByAuthorName(forename,surname);
            return id;
    }

    @Transactional
    public Integer saveIntoJoinTable(final Long bookId, final Long authorId) {
        return authorRepository.save(bookId, authorId);
    }



}
