package com.library.service;

import com.library.domain.BookTag;
import com.library.repository.BookTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookTagService {
    @Autowired
    BookTagRepository bookTagRepository;

    public List<BookTag> getAllBookTags() {
        return bookTagRepository.findAll();

    }
    public BookTag saveBookTag(final BookTag bookTag) {
        return bookTagRepository.save(bookTag);
    }

    public Optional<BookTag> getBookTag(final Long id) {
        return bookTagRepository.findById(id);
    }

    public void deleteBookTag(final Long id) {
        bookTagRepository.deleteById(id);
    }
}

