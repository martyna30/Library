package com.library.service;

import com.library.client.BnClient;
import com.library.domain.bn.ExternalRequestBookDto;
import com.library.domain.bn.ExternalResponseBookDto;
import com.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//@Service
public class BnService {}/*

    @Autowired
    BnClient bnClient;

    @Autowired
    BooksRepository booksRepository;


    public List<ExternalResponseBookDto> getBooks(ExternalRequestBookDto externalRequestBookDto) {
        return bnClient.getBooksFromBn(externalRequestBookDto);
    }

    /*public List<Book> saveBookFromBn(final List<Book>bnBooks) {
        for(Book book: bnBooks) {
            booksRepository.save(book);
        }
        return bnBooks;
    }

    public Iterable <Book>saveBooksFromBn(final Iterable<Book>bnBooks) {
        return booksRepository.saveAll(bnBooks);
    }

}*/
