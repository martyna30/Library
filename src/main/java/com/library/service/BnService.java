package com.library.service;

import com.library.client.BnClient;
import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.domain.bn.BnBook;
import com.library.domain.bn.BnBookDto;
import com.library.repository.BnRepository;
import com.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BnService {

    @Autowired
    BnClient bnClient;

    @Autowired
    BooksRepository booksRepository;

    public List<BnBookDto> getAllBooks() {
        return bnClient.getBooksFromBn();
    }

    public BnBookDto getBook() {
        return bnClient.getBookFromBn();

    }

    public List<Book> saveBookFromBn(final List<Book>bnBooks) {
        for(Book book: bnBooks) {
            booksRepository.save(book);
        }
        return bnBooks;
    }

    public Iterable <Book>saveBooksFromBn(final Iterable<Book>bnBooks) {
        return booksRepository.saveAll(bnBooks);
    }

    public Book saveBookFromBn(final Book book) {
        return booksRepository.save(book);

    }

    public Optional<Book> findById(final Long id) {
        return booksRepository.findById(id);
    }
}
