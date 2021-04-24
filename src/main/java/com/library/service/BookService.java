package com.library.service;

import com.library.domain.Book;
import com.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BooksRepository booksRepository;

    public List<Book> getAllBooks() {
        return booksRepository.findAll();

    }
    public Book saveBook(final Book book) {
        return booksRepository.save(book);
    }

    public Optional<Book> getBook(final Long id) {
        return booksRepository.findById(id);
    }

    public void deleteBook(final Long id) {
        booksRepository.deleteById(id);
    }


}

