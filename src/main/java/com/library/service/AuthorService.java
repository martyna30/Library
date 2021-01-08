package com.library.service;

import com.library.domain.Author;
import com.library.domain.AuthorDto;
import com.library.domain.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthor(final Long id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthor(final Long id) {
        authorRepository.deleteById(id);
    }
}
